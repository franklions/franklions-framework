package com.franklions.example.domain.mapping;

import com.franklions.example.domain.dto.AccessApplicationDTO;
import com.franklions.example.domain.entity.AccessApplicationEntity;
import com.franklions.example.domain.request.AccessApplicationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 实体映射方式，有两种方式一种通过调用INSTANCE 实例变量。一种注入spring bean
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Mapper(componentModel = "spring")
public interface AccessApplicationConvert {
    AccessApplicationConvert INSTANCE = Mappers.getMapper(AccessApplicationConvert.class);
    AccessApplicationEntity req2entity(AccessApplicationRequest app);
    AccessApplicationDTO entity2dto(AccessApplicationEntity entity);
}
