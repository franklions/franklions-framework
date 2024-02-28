package com.franklions.example.injector.methods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.franklions.example.injector.annotation.UpdateField;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/25
 * @since Jdk 1.8
 */
public class BatchInsertAndAppend extends AbstractMethod {
    private static final String[] INSERT_AND_APPEND =new String[] {"batchInsertAndAppend", "插入并更新", "<script>\nINSERT INTO %s %s VALUES %s ON DUPLICATE KEY UPDATE %s\n</script>"};

    /**
     * 字段筛选条件
     */
    @Setter
    @Accessors(chain = true)
    private Predicate<TableFieldInfo> predicate;

    /**
     * 默认方法名
     */
    public BatchInsertAndAppend() {
        super(INSERT_AND_APPEND[0]);
    }

    /**
     * 默认方法名
     *
     * @param predicate 字段筛选条件
     */
    public BatchInsertAndAppend(Predicate<TableFieldInfo> predicate) {
        super(INSERT_AND_APPEND[0]);
        this.predicate = predicate;
    }

    /**
     * @param name      方法名
     * @param predicate 字段筛选条件
     * @since 3.5.0
     */
    public BatchInsertAndAppend(String name, Predicate<TableFieldInfo> predicate) {
        super(name);
        this.predicate = predicate;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        String sqlScript = INSERT_AND_APPEND[2];
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(true, false) +
                this.filterTableFieldInfo(fieldList, predicate, TableFieldInfo::getInsertSqlColumn, EMPTY);
        String columnScript = LEFT_BRACKET + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + RIGHT_BRACKET;
        String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(true, ENTITY_DOT, false) +
                this.filterTableFieldInfo(fieldList, predicate, i -> i.getInsertSqlProperty(ENTITY_DOT), EMPTY);
        insertSqlProperty = LEFT_BRACKET + insertSqlProperty.substring(0, insertSqlProperty.length() - 1) + RIGHT_BRACKET;
        String valuesScript = SqlScriptUtils.convertForeach(insertSqlProperty, "list", null, ENTITY, COMMA);
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (tableInfo.havePK()) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                keyProperty = tableInfo.getKeyProperty();
                // 去除转义符
                keyColumn = removeEscapeCharacter(tableInfo.getKeyColumn());
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(this.methodName, tableInfo, builderAssistant);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }

        //flsh
        // 拿到包含 UpdateField注解的字段
        List<Field> fields = ReflectionKit.getFieldList(ClassUtils.getUserClass(modelClass));
        String updateScript = fields.stream().filter(field -> {
            /* 过滤注解非表字段属性 */
            UpdateField updateField = field.getAnnotation(UpdateField.class);
            return (updateField != null );
        }).map(field -> {
            UpdateField updateField = field.getAnnotation(UpdateField.class);
            String fieldValue = "";

            if(org.apache.commons.lang3.StringUtils.isNotBlank(updateField.value())) {
                fieldValue = updateField.value();
            }else{
                fieldValue =  field.getName();
            }
            return String.format("%s=VALUES(%s)", fieldValue, fieldValue);
        }).collect(Collectors.joining(","));

        if(org.apache.commons.lang3.StringUtils.isBlank(updateScript)){
            sqlScript = sqlScript.replace("ON DUPLICATE KEY UPDATE","");
        }

        String sql = String.format(sqlScript, tableInfo.getTableName(), columnScript, valuesScript,updateScript);
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