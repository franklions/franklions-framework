package com.franklions.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.franklions.example.domain.entity.TemplateEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Mapper
public interface TemplateMapper extends BaseMapper<TemplateEntity> {
    /**
     * 批量插入（mysql）
     * @param entityList
     * @return
     */
    Integer insertBatchSomeColumn(Collection<TemplateEntity> entityList);
}
