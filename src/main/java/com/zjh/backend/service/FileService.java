package com.zjh.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.backend.pojo.entity.Charts;

public interface FileService extends IService<Charts> {
    public void doCreateTable(Long chartId, String fieldName1, String fieldName2);
    public Long doStartTrans(Charts charts);
}
