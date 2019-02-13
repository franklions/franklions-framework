package com.franklions.example.mapper;


import com.franklions.example.domain.UserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author flsh
 * @version 1.0
 * @description
 * 使用真实数据库进行测试
 * @date 2019/2/11
 * @since Jdk 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    @Rollback
    public void selectByName() throws Exception {
        List<UserDO> listUser = userMapper.selectByName("张三");
        assertNotNull(listUser);
        assertEquals(listUser.size(),1);
    }

    @Test
    @Transactional
    @Rollback
    public void selectByAccount() throws Exception {
        UserDO user = userMapper.selectByAccount("admin");
        assertNotNull(user);
        assertEquals(user.getAccount(),"admin");
    }

}
