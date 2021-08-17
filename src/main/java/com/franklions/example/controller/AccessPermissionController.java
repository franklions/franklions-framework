package com.franklions.example.controller;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessPermissionDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.domain.request.AccessPermissionRequest;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.NotFoundResourceException;
import com.franklions.example.service.AccessPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
@RequestMapping("/app/{appId}/permission")
public class AccessPermissionController extends BaseController{

    @Autowired
    private AccessPermissionService permissionService;


    @PostMapping("/create")
    public Boolean createPermission(@PathVariable("appId") String appId,
                              @RequestBody  @NotNull @Valid AccessPermissionRequest request){
        permissionService.createPermission(appId,request);
        return Constants.SUCCESS;
    }

    @PutMapping("/{permissionId}/edit")
    public Boolean editPermission(@PathVariable("appId") String appId,
                            @PathVariable("permissionId") String permissionId,
                            @RequestBody AccessPermissionRequest request){
        //查询该数据是否存在
        Optional<AccessPermissionEntity> entity = permissionService.getPerByAppIdAndPerId(appId, permissionId);
        if (!entity.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        permissionService.editPermission(entity.get(),request);
        return Constants.SUCCESS;
    }

    @DeleteMapping("/{permissionId}/remove")
    public Boolean removePermission(@PathVariable("appId") String appId,
                              @PathVariable("permissionId") String permissionId){
        //查询该数据是否存在
        Optional<AccessPermissionEntity> entity = permissionService.getPerByAppIdAndPerId(appId, permissionId);
        if (entity.isPresent()){
            permissionService.removePermission(entity.get());
        }
        return Constants.SUCCESS;
    }

    @GetMapping("/{permissionId}/get")
    public AccessPermissionDTO loadOne(@PathVariable("appId") String appId,
                                       @PathVariable("permissionId") String permissionId){
        Optional<AccessPermissionDTO> permissionDTO = permissionService.permissionOne(appId, permissionId);
        if(!permissionDTO.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return permissionDTO.get();
    }

    @GetMapping("/list")
    public List<AccessPermissionDTO> loadPermissionList(@PathVariable("appId") String appId){
        List<AccessPermissionDTO> permissionList =permissionService.permissionList(appId);
        if (permissionList==null||permissionList.size()<1){
            return new ArrayList<>();
        }
        return permissionList;
    }

    @GetMapping("/page")
    public PageReturnValue<AccessPermissionDTO> loadPermissionPage(@PathVariable("appId") String appId,
                                                                   @RequestParam(value = "start",required = false,defaultValue = "0") Integer start,
                                                                   @RequestParam(value = "count",required = false,defaultValue = "10") Integer count,
                                                                   @RequestParam(value = "sort",required = false,defaultValue = "id") String sort,
                                                                   @RequestParam(value = "desc",required = false,defaultValue = "false") Boolean desc,
                                                                   @RequestParam(value = "query",required = false) String query) throws IOException {
        PageParamRequest request = new PageParamRequest(start,count,sort,desc,query);
        return permissionService.listPermissionPage(appId,request);
    }
}
