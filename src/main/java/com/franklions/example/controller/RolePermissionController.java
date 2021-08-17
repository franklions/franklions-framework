package com.franklions.example.controller;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.RolePermissionDTO;
import com.franklions.example.domain.entity.AccessPermissionEntity;
import com.franklions.example.exception.ControllerValidationException;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.NotFoundResourceException;
import com.franklions.example.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**用户权限
 * @Author: haoxubo
 * @Date: 2020/10/22 15:17
 */
@Validated
@RestController
@RequestMapping("/app/{appId}")
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加角色权限
     * @param appId
     * @param roleId
     * @param permissions
     * @return
     */
    @PutMapping("/role/{roleId}/permissions")
    public Boolean createRolePermissions(@PathVariable("appId") String appId,
                                  @PathVariable("roleId") String roleId,
                                  @RequestBody @NotNull @Valid List<String> permissions){
        if (permissions==null || permissions.size()<1){
            throw new ControllerValidationException(ErrorCode.PARAMETER_VALID_ERROR);
        }
        //验证该权限是否存在
        List<AccessPermissionEntity> entities=rolePermissionService.permissionsListById(appId,permissions);
        if (entities==null | entities.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        rolePermissionService.createRolePermissions(appId,roleId,entities);
        return Constants.SUCCESS;
    }

    /**
     * 获取用户下权限
     * @param appId
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}/permissions")
    public List<RolePermissionDTO> userPermissionList(@PathVariable("appId") String appId,
                                                      @PathVariable("userId") String userId){
        List<RolePermissionDTO> list= rolePermissionService.userPermissionList(appId,userId);
        if (list==null || list.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return list;
    }

    /**
     * 删除角色权限
     * @param appId
     * @param roleId
     * @param ids
     * @return
     */
    @DeleteMapping("/role/{roleId}/permissions")
    public Boolean removeRolePermissions(@PathVariable("appId") String appId,
                                  @PathVariable("roleId") String roleId,
                                  @RequestParam(name = "ids") List<String> ids){
        if (ids==null || ids.size()<1){
            throw new ControllerValidationException(ErrorCode.PARAMETER_VALID_ERROR);
        }
        rolePermissionService.removeRolePermissions(appId,roleId,ids);
        return Constants.SUCCESS;
    }

    /**
     *获取角色权限
     * @param appId
     * @param roleId
     * @return
     */
    @GetMapping("/role/{roleId}/permissions")
    public List<RolePermissionDTO> rolePermissionList(@PathVariable("appId") String appId,
                                                      @PathVariable("roleId") String roleId){
        List<RolePermissionDTO> list= rolePermissionService.rolePermissionList(appId,roleId);
        if (list==null || list.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return list;
    }

}
