package com.franklions.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.PageReturnValue;
import com.franklions.example.domain.ResponseResult;
import com.franklions.example.domain.entity.TemplateEntity;
import com.franklions.example.domain.request.TemplateRequest;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**访问控制权限
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Validated
@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController{

    @Autowired
    private TemplateService service;


    @PostMapping("/create")
    public ResponseResult createTemplate(@RequestBody  @NotNull @Valid TemplateRequest request){

        Optional<TemplateEntity> templateOpt = service.getOneByName(request.getName());
        if(templateOpt.isPresent()){
            return fail(ErrorCode.EXIST_RECORD_ERROR);
        }
        service.createTemplate(request);
        return  success();
    }

    @PutMapping("/{id}/edit")
    public ResponseResult editTemplate(@PathVariable("id") String id,
                            @RequestBody TemplateRequest request){
        //查询该数据是否存在
        TemplateEntity entity = service.getById(id);
        if (entity == null){
            return fail (ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        service.editTemplate(entity,request);
        return success();
    }

    @DeleteMapping("/{id}/remove")
    public ResponseResult removeTemplate(@PathVariable("id") String id){
        //查询该数据是否存在
        TemplateEntity entity = service.getById(id);
        if (entity != null){
            service.removeTemplate(entity);
        }
        return success();
    }

    @GetMapping("/{id}/get")
    public ResponseResult loadOne(@PathVariable("id") String id){
        TemplateEntity entity = service.getById(id);
        if (entity == null){
            return fail (ErrorCode.NO_FOUND_RECORD_ERROR);
        }


        return success(entity);
    }

    @GetMapping("/list")
    public ResponseResult loadTemplateList(){
        List<TemplateEntity> list =service.list();
        if (list==null||list.size()<1){
            list = new ArrayList<>();
        }
        return success(list);
    }

    @GetMapping("/page")
    public ResponseResult loadTemplatePage(@RequestParam(value = "page",required = false,defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "size",required = false,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value = "sort",required = false,defaultValue = "id") String sort,
                                           @RequestParam(value = "desc",required = false,defaultValue = "false") Boolean desc,
                                           @RequestParam(value = "query",required = false) String query) throws JsonProcessingException {
        PageParamRequest request = new PageParamRequest(pageNum,pageSize,sort,desc,query);
        PageReturnValue<TemplateEntity> pageReturnValue = service.listTemplatePage(request);
        return success(pageReturnValue) ;
    }
}
