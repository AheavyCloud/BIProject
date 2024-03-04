package com.zjh.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.backend.mapper.FileMapper;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Charts>
        implements FileService {
    // 实现业务层代码 为controller层处理数据，利用mapper层数据实现数据的持久化 写入数据库
    @Autowired
    private FileMapper fileMapper;
    public void doCreateTable(Long chartId, String fieldName1, String fieldName2){
        String s = chartId.toString();
        fileMapper.createNewTable(s, fieldName1, fieldName2);

    }

    @Override
    public Long doStartTrans(Charts charts) {
        return fileMapper.startTransaction(charts);

    }


}
