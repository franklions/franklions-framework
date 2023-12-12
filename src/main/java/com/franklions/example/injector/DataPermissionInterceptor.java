package com.franklions.example.injector;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.franklions.example.domain.dto.LoginInfo;
import com.franklions.example.injector.annotation.DataPermission;
import com.franklions.example.service.AccessTokenService;
import com.google.errorprone.annotations.concurrent.LazyInit;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/26
 * @since Jdk 1.8
 */
@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataPermissionInterceptor implements Interceptor {
    //扫描的包路径（根据自己的项目路径来），这里是取的配置里的包路径
    @Value("${permission.package-path}")
    private String packagePath;

    private static List<String> classNames;
    private final static String DEPT_ID = "dept_id";
    private final static String USER_ID = "create_user";

    @Autowired
    private AccessTokenService tokenService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            LoginInfo user = tokenService.getLoginInfo();
            if (user == null){

                return invocation.proceed();

            }

            List<Long> deptIds = user.getDataScope();
            if (deptIds == null){

                deptIds = new ArrayList<>();

            }

            //反射扫包会比较慢，这里做了个懒加载
            if (classNames == null) {
                synchronized (LazyInit.class) {
                    if (classNames == null) {
                        //扫描指定包路径下所有包含指定注解的类
                        Reflections reflections = new Reflections(packagePath);
                        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(DataPermission.class);
                        if (classSet == null && classSet.size() == 0) {
                            classNames = new ArrayList<>();
                        } else {
                            //取得类全名
                            classNames = classSet.stream().map(Class::getName).collect(Collectors.toList());
                        }
                    }
                }
            }

            // 拿到mybatis的一些对象
            StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            // mappedStatement.getId()为执行的mapper方法的全路径名,newId为执行的mapper方法的类全名
            String newId = mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf("."));
            // 如果不是指定的方法，直接结束拦截
            if (!classNames.contains(newId)) {
                return invocation.proceed();
            }
            String newName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length());
            //是否开启数据权限
            boolean isPermi = false;
            Class<?> clazz = Class.forName(newId);
            //遍历方法
            for (Method method : clazz.getDeclaredMethods()) {
                //方法是否含有DataPermission注解，如果含有注解则将数据结果过滤
                if (method.isAnnotationPresent(DataPermission.class) && newName.equals(method.getName())) {
                    DataPermission dataPermission = method.getAnnotation(DataPermission.class);
                    if (dataPermission != null) {
                        //不验证
                        if (!dataPermission.isPermi()) {
                            isPermi = false;
                        } else { //开启验证
                            isPermi = true;
                        }
                    }
                }
            }

            if (isPermi) {
                // 获取到原始sql语句
                String sql = statementHandler.getBoundSql().getSql();
                // 解析并返回新的SQL语句，只处理查询sql
                if (mappedStatement.getSqlCommandType().toString().equals("SELECT")) {
                    //String newSql = getNewSql(sql,deptIds,user.getUserId());
                    sql = getSql(sql,deptIds,user.getUserId());
                }
                // 修改sql
                metaObject.setValue("delegate.boundSql.sql", sql);

            }
            return invocation.proceed();
        } catch (Exception e) {
            System.out.println("=========数据权限隔离异常==" + e);
            return invocation.proceed();
        }

    }

    /**
     * 解析SQL语句，并返回新的SQL语句
     * 注意，该方法使用了JSqlParser来操作SQL，该依赖包Mybatis-plus已经集成了。如果要单独使用，请先自行导入依赖
     *
     * @param sql 原SQL
     * @return 新SQL
     */
    private String getSql(String sql,List<Long> deptIds,Long userId) {

        try {
            String condition = "";
            String permissionSql = "(";
            if (deptIds.size() > 0){
                for (Long deptId : deptIds) {
                    if ("(".equals(permissionSql)){
                        permissionSql = permissionSql + deptId;
                    } else {
                        permissionSql = permissionSql + "," + deptId;
                    }
                }
                permissionSql = permissionSql + ")";
                // 修改原语句
                condition = DEPT_ID +" in " + permissionSql;
            } else {
                condition = USER_ID +" = " + userId;
            }

            if (StringUtils.isBlank(condition)){
                return sql;
            }
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            //取得原SQL的where条件
            final Expression expression = plainSelect.getWhere();
            //增加新的where条件
            final Expression envCondition = CCJSqlParserUtil.parseCondExpression(condition);
            if (expression == null) {
                plainSelect.setWhere(envCondition);
            } else {
                AndExpression andExpression = new AndExpression(expression, envCondition);
                plainSelect.setWhere(andExpression);
            }
            return plainSelect.toString();
        } catch (JSQLParserException e) {
            log.error("解析原SQL并构建新SQL错误：" + e);
            return sql;
        }
    }
}
