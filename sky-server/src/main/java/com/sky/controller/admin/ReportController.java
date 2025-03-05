package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * @author czq
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "数据图表")
public class ReportController {

    @Autowired
    private ReportService  reportService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin
             ,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("营业额数据统计:{},{}",begin,end);
        TurnoverReportVO turnoverReportVO = reportService.getTurnoverReport(begin,end);

        return Result.success(turnoverReportVO);
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> UserReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin
            ,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("用户数据统计:{},{}",begin,end);
        UserReportVO userReportVO = reportService.getuserStatistics(begin,end);

        return Result.success(userReportVO);
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> OrderReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin
            ,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("订单数据统计:{},{}",begin,end);
        OrderReportVO orderReportVO = reportService.OrderStatistics(begin,end);

        return Result.success(orderReportVO);
    }

    @GetMapping("/top10")
    @ApiOperation("Top10数据统计")
    public Result<SalesTop10ReportVO> Top10Report(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin
            ,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

        log.info("Top10数据统计:{},{}",begin,end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.Top10Statistics(begin,end);

        return Result.success(salesTop10ReportVO);
    }
}
