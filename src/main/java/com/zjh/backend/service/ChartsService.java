package com.zjh.backend.service;

import com.zjh.backend.pojo.entity.Charts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartAddRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartDeleteRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartEditRequest;
import com.zjh.backend.pojo.requestbody_.chartrequest.ChartQuaryRequest;

/**
* @author jiahaozhang
* @description 针对表【charts(用户需求信息表)】的数据库操作Service
* @createDate 2024-03-01 22:06:30
*/
public interface ChartsService extends IService<Charts> {
    // 返回生成的图表ID
    public Long doAddChart(ChartAddRequest chartInfo);
    public Integer doDeleteChart(ChartDeleteRequest chartDeleRequest);

    public Integer doEditChart(ChartEditRequest chartEditRequest);

    public Charts doQuaryChartInfo(ChartQuaryRequest chartQuaryRequest);
}
