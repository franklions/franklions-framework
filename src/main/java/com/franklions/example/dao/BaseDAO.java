package com.franklions.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-14
 * @since Jdk 1.8
 */
public abstract class BaseDAO {

        protected List<Predicate> predicates;

        public BaseDAO() {
        }

        protected void buildPredicates() {
            this.predicates = new ArrayList();
        }

        protected List<Predicate> addPredicate(Predicate predicate, Object value) {
            if (!this.isEmpty(value)) {
                this.predicates.add(predicate);
            }

            return this.predicates;
        }

        protected List<Predicate> addPredicate(Predicate predicate) {
            this.predicates.add(predicate);
            return this.predicates;
        }

        protected Predicate[] getPredicateArray() {
            Predicate[] predicateArray = new Predicate[this.predicates.size()];
            return (Predicate[])this.predicates.toArray(predicateArray);
        }

        private boolean isEmpty(Object value) {
            if (value == null) {
                return true;
            } else {
                return value instanceof String ? StringUtils.isEmpty(value) : false;
            }
        }


    @Autowired
    private EntityManager em;

    /**
     * 按sql查询分页
     * findPageBySql
     *
     * @param listSql
     * @param countSql
     * @param queryParams 查询条件
     * @param pageable    分页信息
     * @param clazz       要查询的javabean
     * @return
     * @since Ver 1.0
     */
    protected <E> Page<E> findPageBySql(String listSql, String countSql, Map<String, Object> queryParams, Pageable pageable, Class<E> clazz) {

        Query listQuery = em.createNativeQuery(listSql, clazz);
        Query countQuery = em.createNativeQuery(countSql);

        if (!queryParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                listQuery.setParameter(entry.getKey(), entry.getValue());
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }

        List<E> items = listQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        BigInteger count = (BigInteger) countQuery.getSingleResult();

        return new PageImpl<>(items, pageable, count.longValue());
    }
}
