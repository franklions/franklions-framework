package com.franklions.example.utils;

import java.util.UUID;

/**
 * @author flsh
 * @version 1.0
 * @date 2020/10/21
 * @since Jdk 1.8
 */
public class IdGenerator {
    public static String newUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
