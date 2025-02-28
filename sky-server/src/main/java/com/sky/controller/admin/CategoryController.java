package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分类分页查询
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> pageQuery( CategoryPageQueryDTO categoryPageQueryDTO) {
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 启用禁用分类
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result startOrStop(@PathVariable Integer status,Long id) {
        categoryService.startOrStop(status,id);
        return Result.success();
    }


    /**
     * 修改分类
     */
    @PutMapping
    @ApiOperation("修改分类信息")
    public Result update(@RequestBody  CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);

        return Result.success();
    }

    /**
     * 根据id删除分类
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类信息")
    public Result delete(@RequestParam Long id) {
        categoryService.detele(id);

        return Result.success();
    }
}
