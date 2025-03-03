package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Service
public class SetmealServcieImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 套餐新增
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);


        setmealMapper.save(setmeal);
        //获取返回后的id值付给方法
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
            setmealDishMapper.save(setmealDish);
        }

    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();


        return new PageResult(total, result);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //删除套餐
        setmealMapper.delete(ids);
        //删除套餐与菜品之间的联系
        setmealDishMapper.delete(ids);
    }

    /**
     * 套餐起售或者停止
     *
     * @param status
     * @param id
     */
    @Override
    @Transactional
    public void statusStartOrStop(Integer status, Long id) {
        //当套餐起售时候判断是否有停售的菜品，如果有就不能起售
        if (status == StatusConstant.ENABLE) {
            List<Dish> list = dishMapper.selectWithSetMealDishById(id);
            if (list.size() > 0 && list != null) {
                for (Dish dish : list) {
                    if (dish.getStatus() == StatusConstant.DISABLE) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                }
            }
            Setmeal setmeal = new Setmeal().builder()
                    .status(status)
                    .id(id)
                    .build();
            setmealMapper.update(setmeal);
        }
    }

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SetmealVO ById(Long id) {
        SetmealVO setmealVO = setmealMapper.ById(id);
        //根据id查询相应的setmeal_dish信息
        List<SetmealDish> setmealDishes = setmealDishMapper.ById(id);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 套餐信息修改
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.update(setmeal);
        Long id1 = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Long id = setmealDTO.getId();
        //先删除保存的关联项
        setmealDishMapper.deleteById(id);

        //然后保存关联项

        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id1);
            setmealDishMapper.save(setmealDish);
        }


    }
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
