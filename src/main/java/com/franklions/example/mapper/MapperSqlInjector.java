package com.franklions.example.mapper;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.injector.methods.Upsert;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/11
 * @since Jdk 1.8
 */

public class MapperSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        // 添加InsertBatchSomeColumn方法 真实批量插入，通过单SQL的insert语句实现批量插入；
        methodList.add(new InsertBatchSomeColumn(t -> t.getFieldFill() != FieldFill.UPDATE));

        return methodList;
    }
}
