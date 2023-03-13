package com.franklions.example.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

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
        // TODO Auto-generated method stub
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        methodList.add(new InsertBatchSomeColumn()); // 添加InsertBatchSomeColumn方法
        return methodList;
    }
}
