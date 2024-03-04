package com.zjh.backend.model.dto.requestbody_.chartrequest;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartQuaryRequest {

    private Long id;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表描述
     * */
    private String chartType;

    private Long userId;
}
