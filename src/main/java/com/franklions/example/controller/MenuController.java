package com.franklions.example.controller;

import com.franklions.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-28
 * @since Jdk 1.8
 */
@RestController
public class MenuController {

    @Autowired
    IMenuService menuService;

    @DeleteMapping("/api/menu/{id}")
    public String removeMenu(@PathVariable("id") Integer id){
        menuService.removeMenu(id);
        return "SUCCESS";
    }
}
