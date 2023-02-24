package com.franklions.example.domain.mapping;

import com.franklions.example.domain.entity.TemplateEntity;
import com.franklions.example.domain.request.TemplateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author flsh
 * @version 1.0
 * @date 2023/2/20
 * @since Jdk 1.8
 */
@Mapper(componentModel = "spring")
public interface TemplateConverter {
    TemplateConverter INSTANCE = Mappers.getMapper(TemplateConverter.class);

    @Mappings({
            @Mapping(target = "status",expression = "java(request.getStatus() ==null?0:Integer.valueOf(request.getStatus()))")
    })
    TemplateEntity req2entity(TemplateRequest request);
}
