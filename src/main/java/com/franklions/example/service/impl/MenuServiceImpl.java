package com.franklions.example.service.impl;

import com.franklions.example.repository.MenuRepository;
import com.franklions.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-28
 * @since Jdk 1.8
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    MenuRepository menuRepo;

    @Override
    public void removeMenu(Integer id) {
        menuRepo.deleteById(id);
    }
}
