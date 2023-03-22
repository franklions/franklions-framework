package com.franklions.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/3/22
 * @since Jdk 1.8
 */
public interface CommonMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入
     * @param entityList
     * @return
     */
    int insertBatchSomeColumn(List<T> entityList);

}
