package com.franklions.example.mapper.provider;

import com.franklions.example.domain.PageParamRequest;
import com.franklions.example.domain.dto.RoleUserPageQuery;
import com.franklions.example.domain.entity.RoleUserEntity;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.text.MessageFormat;
import java.util.List;

/**
 * @Author: haoxubo
 * @Date: 2020/10/23 13:15
 */
public class RoleUserMapperProvider {

    public static String selectByPage(@Param("request") PageParamRequest request,
                                      @Param("query") RoleUserPageQuery query){
        StringBuilder sql =  new StringBuilder();
        sql.append(" SELECT br.id,br.device_id AS deviceId,br.batch_num AS batchNum,br.`status`,br.status_update AS statusUpdate,br.remark ,br.gmt_created AS gmtCreated,br.gmt_modified AS gmtModified");
        sql.append("  ,di.device_name AS deviceName,pi.product_id AS productId,pi.product_name AS productName FROM tb_ota_batch_record AS br  ");
        sql.append(createPageSQL(query));
        if(request.getDesc()) {
            sql.append (" order by br."+ CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,  request.getSort()) + " desc");
        }else
        {
            sql.append (" order by br."+CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,  request.getSort()));
        }
        sql.append(" LIMIT "+ request.getStart()+","+request.getCount());
        return sql.toString();
    }

    public static String selectCountByPage(@Param("query") RoleUserPageQuery query){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) FROM tb_ota_batch_record AS br");
        sql.append(createPageSQL(query));
        return sql.toString();
    }

    private static StringBuilder createPageSQL(RoleUserPageQuery query) {
        StringBuilder sql = new StringBuilder();

        sql.append(" INNER JOIN tb_device_info AS di ON di.device_id = br.device_id AND di.deleted =FALSE ");
        sql.append(" LEFT JOIN tb_product_info AS pi ON pi.product_id = di.product_id AND pi.deleted=FALSE ");
        sql.append(" WHERE br.deleted = FALSE ");

        if(StringUtils.isNotBlank(query.getRoleId())){
            sql.append(" AND br.role_id = #{query.roleId} ");
        }

        if(StringUtils.isNotBlank(query.getDeviceName())){
            sql.append(" AND di.device_name like CONCAT('%',#{query.deviceName},'%')");
        }

        return sql;
    }

    public static String insertRoleUser(@Param("list") List<RoleUserEntity> roleUserEntities) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `tb_role_user` (`app_id`, `role_id`, `user_id`, `gmt_created`, `ts`) VALUES ");

        if (roleUserEntities != null && roleUserEntities.size() > 0) {
            MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].appId},#'{'list[{0}].roleId}" +
                    ",#'{'list[{0}].userId},#'{'list[{0}].gmtCreated},#'{'list[{0}].ts})");
            for (int i = 0; i < roleUserEntities.size(); i++) {
                sql.append(messageFormat.format(new Integer[]{i}));
                if (i < roleUserEntities.size() - 1) {
                    sql.append(",");
                }
            }
            sql.append(" ON DUPLICATE KEY UPDATE gmt_created=VALUES(gmt_created),ts=VALUES(ts)");

        }
        return sql.toString();
    }

    public static String roleUserList(@Param("appId") String appId,
                                      @Param("userId") String userId){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ar.role_id AS roleId,ar.role_code AS roleCode,ar.role_name AS roleName,");
        sql.append(" ar.is_admin AS isAdmin,ar.disabled FROM `tb_role_user` AS ru ");
        sql.append(" LEFT JOIN tb_access_role AS ar ON ru.role_id=ar.role_id AND ru.app_id=ar.app_id AND ar.deleted=FALSE");
        sql.append(" WHERE ru.app_id=#{appId} AND ru.user_id=#{userId}");

        return sql.toString();
    }

    public static String mealUserList(@Param("appId") String appId,
                                      @Param("code") String code){
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ru.user_id FROM `tb_role_user` AS ru");
        sql.append(" INNER JOIN tb_role_permission AS rp ON rp.role_id=ru.role_id");
        sql.append(" LEFT JOIN tb_access_permission AS ap ON ap.permission_id=rp.permission_id AND ap.deleted=FALSE");
        sql.append(" WHERE ap.permission_code=#{code} AND ru.app_id=#{appId}");

        return sql.toString();
    }


}
