package com.franklions.example.controller;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessApplicationDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessApplicationEntity;
import com.franklions.example.domain.request.AccessApplicationRequest;
import com.franklions.example.exception.ControllerValidationException;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.NotFoundResourceException;
import com.franklions.example.service.AccessApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/20
 * @since Jdk 1.8
 */
@Validated
@RestController
@RequestMapping("/application")
public class AccessApplicationController extends BaseController {

    @Autowired
    private AccessApplicationService service;

    @PostMapping("/create")
    public Boolean createApplication(@RequestBody @NotNull @Valid AccessApplicationRequest request){
        //验证名称是存已经存在
        Optional<AccessApplicationEntity> entityOpt = service.getAppByName(request.getAppName());
        if(entityOpt.isPresent()){
            throw new ControllerValidationException(ErrorCode.EXIST_RECORD_ERROR);
        }

        service.createApplication(request);
        return Constants.SUCCESS;
    }

    @PutMapping("/{appId}/edit")
    public Boolean editApplication(@PathVariable("appId") String appId,
            @RequestBody AccessApplicationRequest request){
        Optional<AccessApplicationEntity> entityOpt = service.getVerifyByAppId(appId);
        if(!entityOpt.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        service.editApplication(entityOpt.get(),request);
        return Constants.SUCCESS;
    }

    @DeleteMapping("/{appId}/remove")
    public Boolean removeApplication(@PathVariable("appId") String appId){
        Optional<AccessApplicationEntity> entityOpt = service.getVerifyByAppId(appId);
        if(entityOpt.isPresent()){
            service.removeApplication(entityOpt.get());
        }
        return Constants.SUCCESS;
    }

    @GetMapping("/{appId}/get")
    public AccessApplicationDTO loadOne(@PathVariable("appId") String appId){
        Optional<AccessApplicationDTO> entityOpt = service.getAppByAppId(appId);
        if(!entityOpt.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return entityOpt.get();
    }

    @GetMapping("/{appId}/cache/get")
    public AccessApplicationDTO loadCache(@PathVariable("appId") String appId){
        AccessApplicationDTO entity = service.getCacheApp(appId);
        return entity;
    }

    @GetMapping("/list")
    public List<AccessApplicationDTO> loadApplicationList(){
        return service.listAll();
    }

    @GetMapping("/page")
    public PageReturnValue<AccessApplicationDTO> loadApplicationPage(@RequestParam(value = "start",required = false,defaultValue = "0") Integer start,
                                                                     @RequestParam(value = "count",required = false,defaultValue = "10") Integer count,
                                                                     @RequestParam(value = "sort",required = false,defaultValue = "id") String sort,
                                                                     @RequestParam(value = "desc",required = false,defaultValue = "false") Boolean desc,
                                                                     @RequestParam(value = "query",required = false) String query) throws IOException {
        PageParamRequest request = new PageParamRequest(start,count,sort,desc,query);
        return service.listAccessApplicationPage(request);
    }
}
