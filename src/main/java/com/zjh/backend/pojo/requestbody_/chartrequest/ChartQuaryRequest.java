package com.zjh.backend.pojo.requestbody_.chartrequest;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

public class ChartQuaryRequest {

    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表描述
     * */
    private String chartType;

    private Long userId;
}
