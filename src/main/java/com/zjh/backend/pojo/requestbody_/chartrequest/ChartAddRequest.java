package com.zjh.backend.pojo.requestbody_.chartrequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分析目标，图像数据，图表类型
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartAddRequest implements Serializable {


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


}
