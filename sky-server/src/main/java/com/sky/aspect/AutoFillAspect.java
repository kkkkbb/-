package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author czq
 * @version 1.0
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}


    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段的填充");

        //获取到被操作方法的方法类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名
        AutoFill antoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解
        OperationType operationType = antoFill.value();//获得数据库操作类型

        //获取当前被拦截方法的参数-实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object arg = args[0];
        //为实体对象的公共属性来赋值

        LocalDateTime now = LocalDateTime.now();
        Long currentid = BaseContext.getCurrentId();

        //通过反射为属性赋值
        if(operationType == OperationType.INSERT){
            try {
                Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射赋值
                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, currentid);
                setCreateTime.invoke(arg, now);
                setCreateUser.invoke(arg, currentid);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else if(operationType == OperationType.UPDATE){

            try {
                Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射赋值
                setUpdateTime.invoke(arg, now);
                setUpdateUser.invoke(arg, currentid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
