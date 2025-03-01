package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;


    /**
     * 新增菜品
     * @param dishDTO
     */
    @Override
    @Transactional //涉及多个表需要保持事务的一致性
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入1条数据
        dishMapper.insert(dish);

        //获取insert语句生成的主键值
        Long id = dish.getId();

        //向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            //批量导入id
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });

            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        long total = page.getTotal();
        List<DishVO> dishVOS = page.getResult();

        return new PageResult(total,dishVOS);
    }

    /**
     * 菜品批量删除
     * @param ids
     */
    @Override
    @Transactional //涉及多个表操作加事务注解
    public void delete(List<Long> ids) {
        //判断当前菜品是否能够删除-存在起售的菜品
        for (Long id : ids) {
            Dish dish = dishFlavorMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                //当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断是否被套菜关联了，能不能删除

            List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
       if(setmealIdsByDishIds.size() >0 && setmealIdsByDishIds != null){
           throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
       }
        //删除菜品数据
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //删除口味数据
//            dishFlavorMapper.deleteById(id);
//        }

        //根据菜品id集合批量删除菜品数据
        dishMapper.deleteByIds(ids);
       //根据菜品id集合批量删除菜品口味数据
        dishFlavorMapper.deleteByIds(ids);
    }

    @Override
    public DishVO selectByIdWithFlavor(Long id) {

        DishVO dishVO = dishMapper.selectById(id);

        List<DishFlavor> flavors = dishFlavorMapper.selectByIdWithFlavor(id);


        dishVO.getFlavors().addAll(flavors);

        return dishVO;
    }

    /**
     * 修改菜品数据
     * @param dishDTO
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表更新1条数据
        dishMapper.update(dish);

        //删除原先口味数据
        Long id = dishDTO.getId();
        dishFlavorMapper.deleteById(id);

        //插入新增口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(flavors);
        }


    }

    /**
     * 根据id修改status状态 起售或者停售
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {

        dishMapper.updateStatus(status,id);

        if (status == StatusConstant.DISABLE) {
            // 如果是停售操作，还需要将包含当前菜品的套餐也停售
            List<Long> dishIds = new ArrayList<>();
            dishIds.add(id);
            // select setmeal_id from setmeal_dish where dish_id in (?,?,?)
            List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
            if (setmealIds != null && setmealIds.size() > 0) {
                for (Long setmealId : setmealIds) {
                    Setmeal setmeal = Setmeal.builder()
                            .id(setmealId)
                            .status(StatusConstant.DISABLE)
                            .build();
                    setmealMapper.update(setmeal);
                }
            }
        }

    }

    /**
     * 根据category id查询菜品
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> findCategory(Long categoryId) {
        Dish dish = new Dish().builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();

        return dishMapper.selectALl(dish);
    }


}
