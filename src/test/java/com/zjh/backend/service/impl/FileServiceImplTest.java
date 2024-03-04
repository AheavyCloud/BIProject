package com.zjh.backend.service.impl;

import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    private FileService fileService;
    @Test
    void createTableTest() {
//        fileService.doCreateTable(1764579119724511234L, "日期", "数据");
        Charts charts = new Charts();
        // ${goal}, ${chartType}, ${genChart}, ${genResult}, ${userId}
        charts.setUserId(1L);
        charts.setGoal("张嘉豪");
        charts.setChartType("折线图");
        charts.setGenChart("balabala");
        charts.setGenResult("balabalabala");
//        Long chartsId = fileService.doStartTrans(charts);
//        System.out.println(chartsId);
    }

}