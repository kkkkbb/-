package com.sky.service;


import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

/**
 * @author czq
 * @version 1.0
 */
public interface ReportService {


    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);

    /**
     * 用户数据统计
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getuserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO OrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 销量排名统计
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO Top10Statistics(LocalDate begin, LocalDate end);
}
