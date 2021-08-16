//package com.franklions.example.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.sql.DataSource;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
///**
// * @author flsh
// * @version 1.0
// * @description
// * @date 2019/2/13
// * @since Jdk 1.8
// */
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//
//    @MockBean
//    DataSource dataSource;
//
//    @MockBean
//    UserService userService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    ObjectMapper objectMapper;
//    UserDTO user;
//
//    @Before
//    public void setUp() throws Exception {
//        objectMapper = new ObjectMapper();
//        user = new UserDTO();
//    }
//
//    @Test
//    public void testGetAllUser() throws Exception {
//    }
//
//    @Test
//    public void testGetUserByName() throws Exception {
//    }
//
//    @Test
//    public void testGetUserByAccount() throws Exception {
//        when(this.userService.getUserByAccount(anyString())).thenReturn(Optional.ofNullable(user));
//
//        MockHttpServletResponse response = this.mockMvc.perform(get("/api/user/acc")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andReturn().getResponse();
//
//        System.out.println("response:"+response.getContentAsString());
//        assertEquals(HttpStatus.OK.value(),response.getStatus());
//        verify(this.userService).getUserByAccount(anyString());
//    }
//
//}