package com.sky.service.impl;


import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author czq
 * @version 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;


    /*
    营业额统计
     */
    @Override
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算日期，计算日期的后一天的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }


        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询data日期对应的营业额数据，营业额是指，状态已完成的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);//今天开始时间
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);//今天结束时间

            Map map = new HashMap();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO getuserStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算日期，计算日期的后一天的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放每天新增用户数量
        //select count(* from user where create_time < ? and create_time > ?
        List<Integer> newUserList = new ArrayList<>();
        //存放每天用户数量
        List<Integer> totalUserList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询data日期对应的营业额数据，营业额是指，状态已完成的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);//今天开始时间
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);//今天结束时间


            Map map = new HashMap();

            map.put("end", endTime);

            Integer totalUser = userMapper.countByMap(map);
            map.put("begin", beginTime);

            Integer newUser = userMapper.countByMap(map);

            newUserList.add(newUser);
            totalUserList.add(totalUser);

        }


        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO OrderStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算日期，计算日期的后一天的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> OrderCountList = new ArrayList<>();

        List<Integer> validOrderCountList = new ArrayList<>();


        //遍历dateList集合查询每天的有效订单数和订单总数
        for (LocalDate date : dateList) {
            //查询data日期对应的营业额数据，营业额是指，状态已完成的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);//今天开始时间
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);//今天结束时间
            //查询订单总数

            Integer orderall = getOrderCount(beginTime,endTime,null);

            //查询每天有效订单数

            Integer reallyorders = getOrderCount(beginTime,endTime,Orders.COMPLETED);

            OrderCountList.add(orderall);
            validOrderCountList.add(reallyorders);
        }


        Integer totalOrderCount = OrderCountList.stream().reduce(Integer::sum).get();


        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();


        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0){
            orderCompletionRate = (double) validOrderCount / totalOrderCount;
        }

        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(OrderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .validOrderCount(validOrderCount)
                .build();
    }

    /**
     * 销量排名前十
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO Top10Statistics(LocalDate begin, LocalDate end) {

        //查询data日期对应的营业额数据，营业额是指，状态已完成的订单金额合计
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);//今天开始时间
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);//今天结束时间

        List<GoodsSalesDTO> goodsSalesDTO = orderMapper.findGoodsSalesDTO(beginTime, endTime);

        List<String> collect = goodsSalesDTO.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        String nameList = StringUtils.join(collect, ",");

        List<Integer> collect1 = goodsSalesDTO.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String numberList = StringUtils.join(collect1, ",");



        return SalesTop10ReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 根据条件统计订单数量
     * @param begin
     * @param end
     * @param status
     * @return
     */
    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end,Integer status) {
        Map map = new HashMap();
        map.put("beginTime", begin);
        map.put("endTime", end);
        map.put("status",status);

        return orderMapper.getOrdersByMap(map);
    }

}
