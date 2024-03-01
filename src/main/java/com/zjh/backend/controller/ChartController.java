package com.zjh.backend.controller;

import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.pojo.Result;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartAddRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartDeleteRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartEditRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartQuaryRequest;
import com.zjh.backend.service.ChartsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chart") // api统一前缀 -- chart
public class ChartController {

    @Autowired
    ChartsService chartsService;
    // 增加图表信息
    @PostMapping("/add")
    public Result addChartInfo(@RequestBody ChartAddRequest chartInfo){

        if(chartInfo == null)
            throw new BIException(ErrorCode.PARAM_ERROR);
        // 调用service数据插入表
        Long chartId = chartsService.doAddChart(chartInfo);
        return Result.success(chartId);
    }

    @PostMapping("/Delete")
    public Result deleteChart(@RequestBody ChartDeleteRequest chartDelRequest){

        if(chartDelRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 调用service层doDeleteChart方法
        chartsService.doDeleteChart(chartDelRequest);

        return Result.success;
    }

    @PostMapping("/edit")
    public Result editChartInfo(@RequestBody ChartEditRequest chartEditRequest){
        if (chartEditRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 执行service层数据
        Integer editFlag = chartsService.doEditChart(chartEditRequest);

        return Result.success(editFlag.toString());
    }

    @PostMapping("quary")
    public Result quaryChartInfo(@RequestBody ChartQuaryRequest chartQuaryRequest){
        if (chartQuaryRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 执行service层数据查询
        Charts chartInfo = chartsService.doQuaryChartInfo(chartQuaryRequest);

        return Result.success(chartInfo);

    }
}
