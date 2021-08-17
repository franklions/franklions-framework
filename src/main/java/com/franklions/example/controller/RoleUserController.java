package com.franklions.example.controller;

import com.franklions.example.constant.Constants;
import com.franklions.example.domain.dto.RoleUserDTO;
import com.franklions.example.domain.entity.AccessRoleEntity;
import com.franklions.example.exception.ControllerValidationException;
import com.franklions.example.exception.ErrorCode;
import com.franklions.example.exception.NotFoundResourceException;
import com.franklions.example.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**用户角色
 * @Author: haoxubo
 * @Date: 2020/10/22 15:17
 */
@Validated
@RestController
@RequestMapping("/app/{appId}")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    /**
     * 添加用户角色
     * @param appId
     * @param userId
     * @param request
     * @return
     */
    @PutMapping("/user/{userId}/roles")
    public Boolean createRoleUser(@PathVariable("appId") String appId,
                                  @PathVariable("userId") String userId,
                                  @RequestBody List<String> request){
        if (request==null || request.size()<1){
            throw new ControllerValidationException(ErrorCode.PARAMETER_VALID_ERROR);
        }
        //验证该角色是否存在
        List<AccessRoleEntity> roleEntities=roleUserService.roleListById(appId,request);
        if (roleEntities==null || roleEntities.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        roleUserService.createRoleUser(appId,userId,request);
        return Constants.SUCCESS;
    }

    /**
     * 获取用户下的角色
     * @param appId
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}/roles")
    public List<RoleUserDTO> roleUserList(@PathVariable("appId") String appId,
                                          @PathVariable("userId") String userId){
        List<RoleUserDTO> list= roleUserService.roleUserList(appId,userId);
        if (list==null || list.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return list;
    }

    /**
     * 删除用户下的角色
     * @param appId
     * @param userId
     * @param ids
     * @return
     */
    @DeleteMapping("/user/{userId}/roles")
    public Boolean removeRoleUser(@PathVariable("appId") String appId,
                                  @PathVariable("userId") String userId,
                                  @RequestParam(name = "ids") List<String> ids){
        if (ids==null || ids.size()<1){
            throw new ControllerValidationException(ErrorCode.PARAMETER_VALID_ERROR);
        }
        roleUserService.removeRoleUser(appId,userId,ids);
        return Constants.SUCCESS;
    }

    /**
     * 删除全部角色
     * @param appId
     * @param userId
     * @return
     */
    @DeleteMapping("/user/{userId}/roles/all")
    public Boolean removeRoleUserAll(@PathVariable("appId") String appId,
                                  @PathVariable("userId") String userId){
        roleUserService.removeRoleUserAll(appId,userId);
        return Constants.SUCCESS;
    }

    /**
     * 获取拥有此权限的用户
     * @param appId
     * @param code
     * @return
     */
    @GetMapping("/permission/{permissionCode}/users")
    public List<String> permissionCodeUsers(@PathVariable("appId") String appId,
                                          @PathVariable("permissionCode") String code){
        List<String> list= roleUserService.permissionCodeUsers(appId,code);
        if (list==null || list.size()<1){
            throw new NotFoundResourceException(ErrorCode.NO_FOUND_RECORD_ERROR);
        }
        return list;
    }


}
