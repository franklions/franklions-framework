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


    /**
     * 创建模板
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResponseResult createTemplate(@RequestBody  @NotNull @Valid TemplateRequest request){

        Optional<TemplateEntity> templateOpt = service.getOneByName(request.getName());
        if(templateOpt.isPresent()){
            return fail(ErrorCode.EXIST_RECORD_ERROR);
        }
        service.createTemplate(request);
        return  success();
    }

    /**
     * 创建并更新模板
     * @param request
     * @return
     */
    @PutMapping("/save")
    public ResponseResult saveAndUpdateTemplate(@RequestBody  @NotNull @Valid TemplateRequest request){

        Optional<TemplateEntity> templateOpt = service.getOneByName(request.getName());
        if(templateOpt.isPresent()){
            return fail(ErrorCode.EXIST_RECORD_ERROR);
        }
        service.saveAndUpdateTemplate(request);
        return  success();
    }

    /**
     * 批量插入
     * @param requests
     * @return
     */
    @PostMapping("/batch")
    public ResponseResult batchCreateTemplate(@RequestBody  @NotNull @Valid List<TemplateRequest> requests){

        service.saveBatch(requests);
        return success();
    }

    /**
     * 批量插入
     * @param requests
     * @return
     */
    @PostMapping("/batch/save")
    public ResponseResult batchSaveTemplate(@RequestBody  @NotNull @Valid List<TemplateRequest> requests){

        service.batchSaveAndUpdate(requests);
        return success();
    }

    /**
     * 编辑模板
     * @param id
     * @param request
     * @return
     */
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

    /**
     * 逻辑删除模板
     * @param id
     * @return
     */
    @DeleteMapping("/{id}/remove")
    public ResponseResult removeTemplate(@PathVariable("id") String id){
        //查询该数据是否存在
        TemplateEntity entity = service.getById(id);
        if (entity != null){
            service.removeTemplate(entity);
        }
        return success();
    }

    /**
     * 获取模板信息
     * @param id
     * @return
     */
    @GetMapping("/{id}/get")
    public ResponseResult loadOne(@PathVariable("id") String id){
        TemplateEntity entity = service.getById(id);
        if (entity == null){
            return fail (ErrorCode.NO_FOUND_RECORD_ERROR);
        }


        return success(entity);
    }

    /**
     * 获取列表信息
     * @return
     */
    @GetMapping("/list")
    public ResponseResult loadTemplateList(){
        List<TemplateEntity> list =service.list();
        if (list==null||list.size()<1){
            list = new ArrayList<>();
        }
        return success(list);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param desc
     * @param query
     * @return
     * @throws JsonProcessingException
     */
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
