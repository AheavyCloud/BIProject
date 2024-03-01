package com.zjh.backend.pojo.requestbody_.chartrequest;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.Date;

public class ChartEditRequest {

    // 修改那个图表
    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;


}
