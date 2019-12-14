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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-09
 * @since Jdk 1.8
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAO implements IUserDAO {
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
    public void insert(UserDO userDO) {
        userRepo.saveAndFlush(userDO);
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
}
