//package com.franklions.example.mapper;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * @author flsh
// * @version 1.0
// * @date 2019-02-16
// * @since Jdk 1.8
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class RoleMapperTest {
//
//    @Autowired
//    RoleMapper roleMapper;
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @Test
//    public void testSelectRoleMenus(){
//        List<RoleMenusDO> listRole = roleMapper.selectRoleMenus();
//        assertNotNull(listRole);
//        assertNotNull(listRole.get(0).getListMenu());
//    }
//}
