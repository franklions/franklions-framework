CREATE TABLE tb_access_permission(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    permission_id VARCHAR(64) NOT NULL   COMMENT '权限ID' ,
    permission_code VARCHAR(64) NOT NULL   COMMENT '权限编码' ,
    permission_name VARCHAR(128) NOT NULL   COMMENT '权限名称' ,
    permission_type VARCHAR(32)    COMMENT '权限类型(菜单权限、数据权限)' ,
    remark VARCHAR(255)    COMMENT '资源描述' ,
    disabled TINYINT(3)    COMMENT '禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '最后修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '数据是否有效' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '访问控制权限 访问控制权限';;

CREATE TABLE tb_access_role(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    role_id VARCHAR(64) NOT NULL   COMMENT '角色ID' ,
    role_code VARCHAR(64) NOT NULL   COMMENT '角色编码' ,
    role_name VARCHAR(128) NOT NULL   COMMENT '角色名称' ,
    is_admin TINYINT(3) NOT NULL   COMMENT '是否超级管理员' ,
    remark VARCHAR(255)    COMMENT '描述' ,
    disabled TINYINT(3)    COMMENT '禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '最后修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '数据是否有效' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '访问控制角色 访问控制角色';;


CREATE TABLE tb_role_permission(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    role_id VARCHAR(64) NOT NULL   COMMENT '角色ID' ,
    permission_id VARCHAR(64) NOT NULL   COMMENT '权限ID' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '角色权限表 角色权限关联表';;

CREATE TABLE tb_role_user(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    role_id VARCHAR(64) NOT NULL   COMMENT '角色ID' ,
    user_id VARCHAR(64) NOT NULL   COMMENT '用户ID' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '角色用户表 角色用户表';;

CREATE TABLE tb_resource_menu(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    menu_id VARCHAR(64) NOT NULL   COMMENT '菜单ID' ,
    menu_name VARCHAR(200) NOT NULL   COMMENT '菜单名称' ,
    menu_path VARCHAR(512)    COMMENT '菜单路径' ,
    menu_icon VARCHAR(512)    COMMENT '菜单图标' ,
    menu_component VARCHAR(200)    COMMENT '组件' ,
    parent_id VARCHAR(64)    COMMENT '上级菜单ID' ,
    remark VARCHAR(200)    COMMENT '菜单描述' ,
    menu_sort INT    COMMENT '排序' ,
    disabled TINYINT(3) NOT NULL   COMMENT '是否禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '是否删除' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '系统菜单 ';;

CREATE TABLE tb_access_application(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    app_name VARCHAR(200) NOT NULL   COMMENT '应用名称' ,
    remark VARCHAR(200)    COMMENT '描述' ,
    disabled TINYINT(3) NOT NULL   COMMENT '禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '删除' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '权限应用 ';;

CREATE TABLE tb_oauth_client_detail(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    client_id VARCHAR(255) NOT NULL   COMMENT '客户端ID' ,
    client_secret VARCHAR(255) NOT NULL   COMMENT '客户端密码' ,
    resource_id VARCHAR(255)    COMMENT '访问资源ID' ,
    remark VARCHAR(200)    COMMENT '描述' ,
    disabled TINYINT(3) NOT NULL   COMMENT '禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '最后修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '删除' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id)
) COMMENT = '应用访问客户端 ';;

CREATE TABLE tb_oauth_access_token(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    client_id VARCHAR(255) NOT NULL   COMMENT '客户端ID' ,
    user_id VARCHAR(64) NOT NULL   COMMENT '用户ID' ,
    access_token VARCHAR(255) NOT NULL   COMMENT '用户访问token' ,
    expires_in INT NOT NULL   COMMENT '有效时间(秒)' ,
    refresh_token VARCHAR(255)    COMMENT '刷新token' ,
    refresh_expires_in INT    COMMENT '刷新token有效时间' ,
    disabled TINYINT(3) NOT NULL   COMMENT '禁用' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '最后修改时间' ,
    deleted TINYINT(3) NOT NULL   COMMENT '删除' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id,app_id,client_id,user_id,access_token,expires_in,disabled,gmt_created,gmt_modified,deleted,ts)
) COMMENT = '用户访问token ';;

CREATE TABLE tb_oauth_code(
    id BIGINT NOT NULL AUTO_INCREMENT  COMMENT '记录ID 自增列记录ID' ,
    app_id VARCHAR(64) NOT NULL   COMMENT '应用ID' ,
    client_id VARCHAR(255) NOT NULL   COMMENT '客户端ID' ,
    user_id VARCHAR(64) NOT NULL   COMMENT '用户ID' ,
    authorization_code VARCHAR(255) NOT NULL   COMMENT '临时code' ,
    gmt_created BIGINT NOT NULL   COMMENT '创建时间' ,
    gmt_modified BIGINT NOT NULL   COMMENT '最后修改时间' ,
    ts BIGINT NOT NULL   COMMENT '时间戳' ,
    PRIMARY KEY (id,app_id,client_id,user_id,authorization_code,gmt_created,gmt_modified,ts)
) COMMENT = '获取token时临时code ';;