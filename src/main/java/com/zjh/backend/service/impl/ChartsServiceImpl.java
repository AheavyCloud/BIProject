package com.zjh.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartAddRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartDeleteRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartEditRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartQuaryRequest;
import com.zjh.backend.service.ChartsService;
import com.zjh.backend.mapper.ChartsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author jiahaozhang
* @description 针对表【charts(用户需求信息表)】的数据库操作Service实现
* @createDate 2024-03-01 22:06:30
*/
@Slf4j
@Service
public class ChartsServiceImpl extends ServiceImpl<ChartsMapper, Charts>
    implements ChartsService{

    @Autowired
    ChartsMapper chartsMapper;
    @Override
    public Long doAddChart(ChartAddRequest chartInfo) {

        // 数据转换
        Charts singleChart = new Charts();
        singleChart.setGoal(chartInfo.getGoal());
        singleChart.setChartData(chartInfo.getChartData());
        singleChart.setChartType(chartInfo.getChartType());

        if(!this.save(singleChart)){
            throw new BIException(ErrorCode.PARAM_ERROR);
        }
        // todo 插入成功以后返回什么数据呢？ -- 返回数据表id！
        return singleChart.getId();

    }

    @Override
    public Integer doDeleteChart(ChartDeleteRequest chartDeleRequest) {

        boolean deleFlag = removeById(chartDeleRequest.getId());

        return deleFlag ? 1: -1;
    }

    @Override
    public Integer doEditChart(ChartEditRequest chartEditRequest) {
        Charts editCharts = new Charts();
        BeanUtils.copyProperties(chartEditRequest, editCharts);
        boolean editFlag = updateById(editCharts);
        return editFlag ? 1 : -1;

    }

    @Override
    public List<Charts> doQuaryChartInfo(ChartQuaryRequest chartQuaryRequest) {
        Charts quaryChart = new Charts();
        BeanUtils.copyProperties(chartQuaryRequest, quaryChart);
        log.info("正在查询charts表" + quaryChart);
        List<Charts> charts = chartsMapper.selectChartByChartRequest(quaryChart);
        return charts;
    }
}




