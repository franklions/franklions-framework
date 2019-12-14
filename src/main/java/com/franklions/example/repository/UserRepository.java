package com.franklions.example.repository;

import com.franklions.example.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/** JpaSpecificationExecutor 实现动态查询接口
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
public interface UserRepository extends JpaRepository<UserDO,Integer>, JpaSpecificationExecutor<UserDO> {
    List<UserDO> findByNameLike(String name);

    UserDO findByAccount(String account);

//    /**
//     * 使用本地sql nativeQuery = true
//     * @param ids
//     * @param updatedBy
//     * @param updatedDate
//     * @return
//     */
//    @Modifying
//    @Query(value = "update sys_user set deleted = true, updated_by = :updatedBy, updated_date = :updatedDate where id in :ids", nativeQuery = true)
//    int deleteUser(@Param("ids") String[] ids,
//                        @Param("updatedBy") String updatedBy,
//                        @Param("updatedDate") Date updatedDate);
//
//    /**
//     * 使用PSQL 使用对象和属于字段
//     * @param ids
//     * @param updatedBy
//     * @param updatedDate
//     * @return
//     */
//    @Modifying
//    @Query("update UserDO t set t.deleted = true, t.updatedBy = :updatedBy, t.updatedDate = :updatedDate where t.id in :ids")
//    int deleteUser2(@Param("ids") String[] ids,
//                       @Param("updatedBy") String updatedBy,
//                       @Param("updatedDate") Date updatedDate);
}
