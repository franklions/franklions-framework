package com.franklions.example.utils;

import com.franklions.example.aspect.annotation.RequiresPermissions;
import com.franklions.example.exception.NotPermissionException;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @date 2024/4/2
 * @since Jdk 1.8
 */
public class AuthUtil {
    public static void checkPermi(List<String> authorities, RequiresPermissions requiresPermissions) {
        for (String permission : requiresPermissions.value()) {
            boolean authMatch = authorities.stream().filter(StringUtils::hasText).anyMatch(p -> PatternMatchUtils.simpleMatch(p, permission));
            if(!authMatch){
                throw new NotPermissionException(permission);
            }

        }
    }
}
