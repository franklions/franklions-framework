package com.franklions.example.controller;

import com.franklions.example.domain.DeptDO;
import com.franklions.example.domain.DeptDTO;
import com.franklions.example.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-12-11
 * @since Jdk 1.8
 */
@Validated
@RestController
public class DeptController {

    @Autowired
    IDeptService deptService;

    @GetMapping("/api/dept/{id}")
    public DeptDO getDeptInfo(@PathVariable(value = "id",required = false) @NotNull Integer id){
        Optional<DeptDO> deptOpt = deptService.getDeptInfoById(id);

        return deptOpt.get();
    }

    @PostMapping("/api/dept")
    public String addDeptInfo(@NotNull @Valid @RequestBody DeptDTO request){
        deptService.addDept(request);
        return "SUCCESS";
    }

    @DeleteMapping("/api/dept/{id}")
    public String removeDept(@PathVariable("id") Integer id){
        deptService.removeDept(id);
        return "SUCCESS";
    }
}
