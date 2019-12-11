//package com.franklions.example.service;
//
//import com.franklions.example.domain.UserDO;
//import com.franklions.example.domain.UserDTO;
//import com.franklions.example.repository.UserMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.*;
//
///**
// * @author flsh
// * @version 1.0
// * @description
// * @date 2019/2/13
// * @since Jdk 1.8
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceTest {
//
//    @MockBean
//    UserMapper userMapper;
//
//    @Autowired
//    UserService userService;
//
//    UserDO user;
//
//    @Before
//    public void initData(){
//        user = new UserDO();
//        user.setId(1);
//        user.setAccount("admin");
//        user.setName("管理员");
//    }
//
//    @Test
//    public void getAllUsers() throws Exception {
//        List<UserDO> allUsers = new ArrayList<UserDO>(){{add(user);add(user);add(user);add(user);add(user);}};
//        when(this.userMapper.selectAll()).thenReturn(allUsers);
//        List<UserDTO> result = this.userService.getAllUsers();
//        assertEquals(result.get(0).getId(), allUsers.get(0).getId());
//        assertNotNull(result);
//        verify(this.userMapper,times(1)).selectAll();
//    }
//
//    @Test
//    public void getUserByName() throws Exception {
//        List<UserDO> allUsers = new ArrayList<UserDO>(){{add(user);add(user);add(user);add(user);add(user);}};
//        when(this.userMapper.selectByName(anyString())).thenReturn(allUsers);
//        List<UserDTO> result = this.userService.getUserByName("管理员");
//        assertNotNull(result);
//        verify(this.userMapper,times(1)).selectByName(anyString());
//    }
//
//    @Test
//    public void getUserByAccount() throws Exception {
//        when(this.userMapper.selectByAccount(anyString())).thenReturn(user);
//         Optional<UserDTO> userDTO = this.userService.getUserByAccount("admin");
//        assertNotNull(userDTO);
//        verify(this.userMapper,times(1)).selectByAccount(anyString());
//    }
//
//}
