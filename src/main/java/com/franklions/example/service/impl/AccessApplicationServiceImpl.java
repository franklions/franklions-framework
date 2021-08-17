package com.franklions.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessApplicationDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessApplicationEntity;
import com.franklions.example.domain.mapping.AccessApplicationConvert;
import com.franklions.example.domain.request.AccessApplicationRequest;
import com.franklions.example.mapper.AccessApplicationMapper;
import com.franklions.example.service.AccessApplicationService;
import com.franklions.example.utils.BeanUtils;
import com.franklions.example.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/20
 * @since Jdk 1.8
 */
@Service
public class AccessApplicationServiceImpl implements AccessApplicationService {

    private static final String APPNAME = "appName";
    private AccessApplicationMapper mapper;
    private ObjectMapper objectMapper;
    private AccessApplicationConvert convert;

    @Autowired
    public AccessApplicationServiceImpl(AccessApplicationMapper mapper,
                                        ObjectMapper objectMapper,
                                        AccessApplicationConvert convert) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.convert = convert;
    }

    @Override
    public List<AccessApplicationDTO> listAll() {
        List<AccessApplicationEntity> list = mapper.selectAll();
        if(list==null || list.size()<1){
            return  new ArrayList<>();
        }
        List<AccessApplicationDTO> retList = list.stream().map(a ->convert.entity2dto(a))
                .collect(Collectors.toList());

        return retList;
    }

    @Override
    public Optional<AccessApplicationDTO> getAppByAppId(String appId) {
        AccessApplicationEntity entity = mapper.selectOne(new AccessApplicationEntity(){{
            setAppId(appId);
            setDeleted(false);
        }});
        AccessApplicationDTO accessApplicationDTO = convert.entity2dto(entity);
        return Optional.ofNullable(accessApplicationDTO);
    }

    @Override
    public PageReturnValue<AccessApplicationDTO> listAccessApplicationPage(PageParamRequest request) throws IOException {
        AccessApplicationEntity entity = new AccessApplicationEntity();

        if (request.getQueryStr()!= null && !"".equals(request.getQueryStr())) {
            entity = objectMapper.readValue(request.getQueryStr(),AccessApplicationEntity.class);
        }
        Example example = new Example(AccessApplicationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(Constants.DELETED,false);
        //创建查询条件

        if (StringUtils.isNotBlank(entity.getAppName() )) {
            criteria.andLike(APPNAME, "%"+entity.getAppName()+"%");
        }


        //排序方式
        if(request.getDesc()){
            example.orderBy(request.getSort()).desc();
        }else{
            example.orderBy(request.getSort()).asc();
        }
        //分页
        RowBounds rowBounds = new RowBounds(request.getStart(), request.getCount());

        List<AccessApplicationEntity> listApplication =mapper.selectByExampleAndRowBounds(example, rowBounds);

        if (listApplication == null || listApplication.size() < 1) {
            return PageReturnValue.empty();
        }
        List<AccessApplicationDTO> retList = listApplication.stream().map(a->convert.entity2dto(a))
                .collect(Collectors.toList());
        //获取总数
        Integer total = mapper.selectCountByExample(example);

        PageReturnValue<AccessApplicationDTO> returnValue = new PageReturnValue<AccessApplicationDTO>(retList, total);

        return returnValue;
    }

    @Override
    public Optional<AccessApplicationEntity> getAppByName(String appName) {
        AccessApplicationEntity accessApplication = mapper.selectOne(new AccessApplicationEntity(){{
            setDeleted(false);
            setAppName(appName);
        }});

        return Optional.ofNullable(accessApplication);
    }

    @Override
    public void createApplication(AccessApplicationRequest request) {
        AccessApplicationEntity newEntity = convert.req2entity(request);
        newEntity.setAppId(IdGenerator.newUUID());
        newEntity.setCreateData();
        mapper.insert(newEntity);
    }

    @Override
    public void editApplication(AccessApplicationEntity entity, AccessApplicationRequest request) {
        AccessApplicationEntity source = convert.req2entity(request);
        BeanUtils.mergeObject(entity,source);
        entity.setGmtModified(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public void removeApplication(AccessApplicationEntity entity) {
        entity.setDeleted(true);
        entity.setGmtModified(System.currentTimeMillis());
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public Optional<AccessApplicationEntity> getVerifyByAppId(String appId) {
        AccessApplicationEntity entity = mapper.selectOne(new AccessApplicationEntity(){{
            setAppId(appId);
            setDeleted(false);
        }});
        return Optional.ofNullable(entity);
    }
}
