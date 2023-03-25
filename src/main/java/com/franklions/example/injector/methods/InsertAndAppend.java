package com.franklions.example.injector.methods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.franklions.example.injector.annotation.UpdateField;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/25
 * @since Jdk 1.8
 */
public class InsertAndAppend extends AbstractMethod {

    private static final String[] INSERT_AND_APPEND =new String[] {"insertAndAppend", "插入并更新", "<script>\nINSERT INTO %s %s VALUES %s ON DUPLICATE KEY UPDATE %s\n</script>"};

    public InsertAndAppend() {
        this(INSERT_AND_APPEND[0]);
    }

    /**
     * @param name 方法名
     * @since 3.5.0
     */
    public InsertAndAppend(String name) {
        super(name);
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        String sqlScript = INSERT_AND_APPEND[2];
        String columnScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlColumnMaybeIf(null),
                LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
        String valuesScript = SqlScriptUtils.convertTrim(tableInfo.getAllInsertSqlPropertyMaybeIf(null),
                LEFT_BRACKET, RIGHT_BRACKET, null, COMMA);
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                keyProperty = tableInfo.getKeyProperty();
                // 去除转义符
                keyColumn = removeEscapeCharacter(tableInfo.getKeyColumn());
            } else if (null != tableInfo.getKeySequence()) {
                keyGenerator = TableInfoHelper.genKeyGenerator(methodName, tableInfo, builderAssistant);
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            }
        }
        //flsh
        // 拿到包含 UpdateField注解的字段
        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(modelClass));
        String updateScript = fieldList.stream().filter(field -> {
            /* 过滤注解非表字段属性 */
            UpdateField updateField = field.getAnnotation(UpdateField.class);
            return (updateField != null && org.apache.commons.lang3.StringUtils.isNotBlank(updateField.value()));
        }).map(field -> {
            UpdateField updateField = field.getAnnotation(UpdateField.class);
            return String.format("%s=VALUES(%s)", updateField.value(),updateField.value());
        }).collect(Collectors.joining(","));

        if(org.apache.commons.lang3.StringUtils.isBlank(updateScript)){
            sqlScript = sqlScript.replace("ON DUPLICATE KEY UPDATE","");
        }

        String sql = String.format(sqlScript, tableInfo.getTableName(), columnScript, valuesScript, updateScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, methodName, sqlSource, keyGenerator, keyProperty, keyColumn);
    }

    /**
     * 刪除字段转义符单引号双引号
     *
     * @param text 待处理字段
     * @return
     */
    private  String removeEscapeCharacter(String text) {
        Objects.nonNull(text);
        return text.replaceAll("\"", "").replaceAll("'", "");
    }

}
