package com.franklions.example.controller;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.AccessRoleDTO;
import com.franklions.example.domain.dto.PageParamRequest;
import com.franklions.example.domain.dto.PageReturnValue;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.domain.request.AccessRoleRequest;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.NotFoundResourceException;
import com.franklions.example.service.AccessRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**访问控制角色
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
@Validated
@RestController
@RequestMapping("/app/{appId}/role")
public class AccessRoleController extends BaseController{

    @Autowired
    private AccessRoleService accessRoleService;


    @PostMapping("/create")
    public Boolean createRole(@PathVariable("appId") String appId,
                              @RequestBody  @NotNull @Valid AccessRoleRequest request){
        accessRoleService.createRole(appId,request);
        return Constants.SUCCESS;
    }

    @PutMapping("/{roleId}/edit")
    public Boolean editRole(@PathVariable("appId") String appId,
                            @PathVariable("roleId") String roleId,
                            @RequestBody AccessRoleRequest request){
        //查询该数据是否存在
        Optional<AccessRoleEntity> entity = accessRoleService.getRoleByAppIdAndRoleId(appId, roleId);
        if (!entity.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        accessRoleService.editRole(entity.get(),request);
        return Constants.SUCCESS;
    }

    @DeleteMapping("/{roleId}/remove")
    public Boolean removeRole(@PathVariable("appId") String appId,
                              @PathVariable("roleId") String roleId){
        //查询该数据是否存在
        Optional<AccessRoleEntity> entity = accessRoleService.getRoleByAppIdAndRoleId(appId, roleId);
        if (entity.isPresent()){
            accessRoleService.removeRole(entity.get());
        }
        return Constants.SUCCESS;
    }

    @GetMapping("/{roleId}/get")
    public AccessRoleDTO loadOne(@PathVariable("appId") String appId,
                                 @PathVariable("roleId") String roleId){
        Optional<AccessRoleDTO> accessRoleEntity = accessRoleService.roleOne(appId, roleId);
        if(!accessRoleEntity.isPresent()){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return accessRoleEntity.get();
    }

    @GetMapping("/list")
    public List<AccessRoleDTO> loadRoleList(@PathVariable("appId") String appId){
        List<AccessRoleDTO> roleList =accessRoleService.RoleList(appId);
        if (roleList==null||roleList.size()<1){
            return new ArrayList<>();
        }
        return roleList;
    }

    @GetMapping("/page")
    public PageReturnValue<AccessRoleDTO> loadRolePage(@PathVariable("appId") String appId,
                                                       @RequestParam(value = "start",required = false,defaultValue = "0") Integer start,
                                                       @RequestParam(value = "count",required = false,defaultValue = "10") Integer count,
                                                       @RequestParam(value = "sort",required = false,defaultValue = "id") String sort,
                                                       @RequestParam(value = "desc",required = false,defaultValue = "false") Boolean desc,
                                                       @RequestParam(value = "query",required = false) String query) throws IOException {
        PageParamRequest request = new PageParamRequest(start,count,sort,desc,query);
        return accessRoleService.listAccessRolePage(appId,request);
    }
}
