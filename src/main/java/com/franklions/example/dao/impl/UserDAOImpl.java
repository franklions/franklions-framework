package com.franklions.example.dao.impl;

import com.franklions.example.dao.BaseDAO;
import com.franklions.example.dao.IUserDAO;
import com.franklions.example.domain.UserDO;
import com.franklions.example.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAO implements IUserDAO {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepo;

    @Override
    public List<UserDO> selectAll() {
        return userRepo.findAll();
    }

    @Override
    public List<UserDO> selectByName(String name) {
        return userRepo.findByNameLike(name);
    }

    @Override
    public UserDO selectByAccount(String account) {
        return userRepo.findByAccount(account);
    }

    @Override
    public Optional<UserDO> selectUserDetialById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public UserDO insert(UserDO userDO) {
       return userRepo.saveAndFlush(userDO);
    }

//    /**
//     * 构造动态查询语句
//     * @param id
//     * @return
//     */
//    public Optional<UserDO> findOne(Integer id){
//        Optional<UserDO> optional = userRepo.findOne((root, query, cb) -> {
//
//            root.fetch("deptDO", JoinType.LEFT);
//
//            super.buildPredicates();
//
//            super.addPredicate(cb.isFalse(root.get("deleted")));
//            super.addPredicate(cb.equal(root.get("id"), id));
//
//            return cb.and(super.getPredicateArray());
//        });
//        return optional;
//    }

    /**
     * 利用sql语句进行查询
     * @param stationId
     * @param pageable
     * @return
     */
    public Page<UserDO> findUnboundPiles(String stationId, Pageable pageable) {

        Map<String, Object> queryParams = new HashMap<>();

        Map<String, String> sqlMap = sqlOfFindUnboundPiles(stationId, queryParams);
        String listSql = sqlMap.get("LIST_SQL");
        String countSql = sqlMap.get("COUNT_SQL");

        return super.findPageBySql(listSql, countSql, queryParams, pageable, UserDO.class);
    }

    private Map<String, String> sqlOfFindUnboundPiles(String stationId, Map<String, Object> queryParams) {

        StringBuffer listSql = new StringBuffer();
        listSql.append("select t.id, t.pile_asset_encoding\n");
        listSql.append("from (\n");
        listSql.append("         select tcp.id, tcp.pile_asset_encoding, tcp.substation_id, trmp.pile_id\n");
        listSql.append("         from t_charging_pile tcp\n");
        listSql.append("                  left join t_relp_meter_pile trmp on tcp.id = trmp.pile_id\n");
        listSql.append("         where tcp.deleted = false\n");
        listSql.append("     ) t\n");
        listSql.append("where t.pile_id is null\n");

        if (StringUtils.isNotBlank(stationId)) {
            listSql.append("  and t.substation_id = :stationId");
            queryParams.put("stationId", stationId);
        }

        StringBuffer countSql = new StringBuffer();
        countSql.append("select count(t1.id) from (\n");
        countSql.append(listSql);
        countSql.append(") t1\n");

        Map<String, String> map = new HashMap<>();
        map.put("LIST_SQL", listSql.toString());
        map.put("COUNT_SQL", countSql.toString());

        return map;
    }

    public List<UserDO> findListBySQL(String svcWorkOrderCode,
                              String deviceName,
                              String currentStatus,
                              String involveStation,
                              String acceptOpTeam,
                              String transRepair,
                              Date createBeginDate,
                              Date createEndDate){

        StringBuffer sql = new StringBuffer();
        Map<String, Object> params = new HashMap<>();


        sql.append(" select \n");
        sql.append(" oc.work_order_type,\n");
        sql.append(" count(1) as order_count \n");
        sql.append(" from \n");
        sql.append(" t_work_order oc\n");
        sql.append(" where oc.current_status = '待处理'\n");


        if (StringUtils.isNotBlank(svcWorkOrderCode)) {
            sql.append("    and oc.svc_work_order_code like :svcWorkOrderCode\n");
            params.put("svcWorkOrderCode", svcWorkOrderCode);
        }

        if (StringUtils.isNotBlank(deviceName)) {
            sql.append("    and oc.device_name like :deviceName \n");
            params.put("deviceName", deviceName);
        }

        if (StringUtils.isNotBlank(currentStatus)) {
            sql.append("    and oc.current_status like :currentStatus\n");
            params.put("currentStatus", currentStatus);
        }

        if (StringUtils.isNotBlank(involveStation)) {
            sql.append("    and oc.involve_station like :involveStation\n");
            params.put("involveStation", involveStation);
        }

        if (StringUtils.isNotBlank(acceptOpTeam)) {
            sql.append("     and oc.accept_op_team like :acceptOpTeam\n");
            params.put("acceptOpTeam", acceptOpTeam);
        }

        if (StringUtils.isNotBlank(transRepair)) {
            sql.append("     and oc.trans_repair like :transRepair\n");
            params.put("transRepair", transRepair);
        }

        if (null != createBeginDate) {
            sql.append("  and oc.create_date > :createBeginDate\n");
            params.put("createBeginDate", createBeginDate);
        }

        if (null != createEndDate) {
            sql.append("  and oc.create_date < :createEndDate\n");
            params.put("createEndDate", createEndDate);
        }

        sql.append(" group by oc.work_order_type\n");

        //TODO 注意这里的 UserDO.class 很重要，必须要写，不然导致获取属性错误问题
        Query listQuery = em.createNativeQuery(sql.toString(),UserDO.class);

        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                listQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }

        List<UserDO> listResult = listQuery.getResultList();
        return listResult;
    }
}
