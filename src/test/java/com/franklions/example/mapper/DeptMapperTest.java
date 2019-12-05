package com.franklions.example.mapper;

import com.franklions.example.domain.DeptUserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2019/2/15
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeptMapperTest {

    @Autowired
    DeptMapper deptMapper;

    @Test
    @Transactional
    @Rollback
    public void testSelectDeptUsersById() throws Exception {
        DeptUserDO deptUserDO = deptMapper.selectDeptUsersById(24);
        assertNotNull(deptUserDO);
        if(deptUserDO != null) {
            assertNotNull(deptUserDO.getListUser());
            assertTrue(deptUserDO.getListUser().size() > 0);
        }
    }

}