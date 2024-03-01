package com.zjh.backend.pojo.requestbody_.chartrequest;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDeleteRequest {

    // 根据id删除
    private Long id;

}
