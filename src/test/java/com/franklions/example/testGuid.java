package com.franklions.example;

import org.junit.Test;

import java.util.UUID;

/**
 * @author flsh
 * @version 1.0
 * @date 2021/9/27
 * @since Jdk 1.8
 */
public class testGuid {

    @Test
    public void testUUid(){
        for (int i=0;i<13;i++){
            System.out.println(UUID.randomUUID().toString().replace("-",""));
        }
    }
}
