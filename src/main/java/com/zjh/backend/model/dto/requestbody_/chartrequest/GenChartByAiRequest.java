package com.zjh.backend.model.dto.requestbody_.chartrequest;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 * //用于 获取生成图片信息数据：用户传入的数据
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 图表名称
     */
    private String name;

    /**
     *  目标生成数据
     * */
    private String goal;
    /**
     * 图表类型
     * */
    private String chartType;

    private static final long serialVersionUID = 1L;
}