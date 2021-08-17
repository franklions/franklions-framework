package com.franklions.example.mapper.provider;

import com.franklions.example.domain.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.text.MessageFormat;
import java.util.List;

/**
 * @Author: haoxubo
 * @Date: 2020/10/23 16:39
 */
public class RolePermissionProvider {

    public static String userPermissionList(@Param("appId") String appId,
                                      @Param("userId") String userId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ap.permission_id AS permissionId,ap.permission_code AS permissionCode,");
        sql.append(" ap.permission_name AS permissionName,ap.permission_type AS permissionType,");
        sql.append(" ap.disabled FROM `tb_role_user` AS ru ");
        sql.append(" LEFT JOIN tb_role_permission AS rp ON ru.app_id=rp.app_id AND ru.role_id=rp.role_id");
        sql.append(" INNER JOIN tb_access_permission AS ap ON rp.app_id=ap.app_id AND rp.permission_id=ap.permission_id");
        sql.append(" AND ap.deleted=FALSE");
        sql.append(" WHERE ru.app_id=#{appId} AND ru.user_id=#{userId}");

        return sql.toString();
    }

    public static String createRolePermissions(@Param("list") List<RolePermissionEntity> entities) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `tb_role_permission` (`app_id`, `role_id`, `permission_id`, `gmt_created`, `ts`) VALUES ");

        if (entities != null && entities.size() > 0) {
            MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].appId},#'{'list[{0}].roleId}" +
                    ",#'{'list[{0}].permissionId},#'{'list[{0}].gmtCreated},#'{'list[{0}].ts})");
            for (int i = 0; i < entities.size(); i++) {
                sql.append(messageFormat.format(new Integer[]{i}));
                if (i < entities.size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(" ON DUPLICATE KEY UPDATE gmt_created=VALUES(gmt_created),ts=VALUES(ts)");

        }
        return sql.toString();
    }

    public static String rolePermissionList(@Param("appId") String appId,
                                            @Param("roleId") String roleId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ap.permission_id AS permissionId,ap.permission_code AS permissionCode,");
        sql.append(" ap.permission_name AS permissionName,ap.permission_type AS permissionType,");
        sql.append(" ap.disabled FROM `tb_role_permission` AS rp");
        sql.append(" LEFT JOIN tb_access_permission AS ap ON rp.app_id=ap.app_id AND rp.permission_id=ap.permission_id ");
        sql.append(" AND ap.deleted=FALSE");
        sql.append(" WHERE rp.app_id=#{appId} AND rp.role_id=#{roleId}");

        return sql.toString();
    }
}
