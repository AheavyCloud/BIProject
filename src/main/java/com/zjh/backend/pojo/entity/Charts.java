package com.zjh.backend.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户需求信息表
 * @TableName charts
 */
@TableName(value ="charts")
@Data
public class Charts implements Serializable {
    /**
     * 图表id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * AI生成的图表信息
     */
    private String genChart;

    /**
     * 图表描述
     * */
    private String chartType;
    /**
     * 用户id
     * */
    private Long userId;
    /**
     * 生成的分析结论
     */
    private String genResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;


    private static final long serialVersionUID = 1L;


}