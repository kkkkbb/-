package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author czq
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "套餐相关功能")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 添加套餐分类
     * @param categorydto
     * @return
     */
    @PostMapping
    @ApiOperation("添加套餐")
    public Result save(@RequestBody  CategoryDTO categorydto) {
        log.info("套餐新增，参数为：{}", categorydto);
        categoryService.save(categorydto);

        return Result.success();
    }


}
