package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.entity.SetmealDish;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @PostMapping
    @ApiOperation("套餐新增")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐{}",setmealDTO);

        setmealService.save(setmealDTO);
        return Result.success();

    }


}
