package com.zjh.backend.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zjh.backend.controller.FileController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 使用easy excel文件库对excel文件进行接收
 * ESCEL -> CSV 格式
 * */
@Slf4j
public class ExcelUtils {
    public static String  excelToCsv(MultipartFile multipartFile) {
        // 本地文件
//        File file = null;
//        try {
//            file = ResourceUtils.getFile("classpath:charttest.xlsx");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        // 读取网络文件：
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("表格处理错误", e);
        }

        if(CollUtil.isEmpty(list)){
            return "";
        }
        // 将字符串拼接起来 -- StringBuilder 线程不安全但是效率高
        StringBuilder stringBuilder = new StringBuilder();
        // 读取表头数据
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap<Integer, String>) list.get(0);
        // 使用过滤器将空字符串null过滤出来
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils :: isNotEmpty).collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList, ",")).append("\n");

        // 字符串拼接 , 将值取出来

        // 读取数据
        for(int i = 1; i < list.size(); i++){
            // 读取其余数据
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap<Integer, String>) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils :: isNotEmpty).collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(dataList, ",")).append("\n");


        }

        System.out.println(stringBuilder);
        return stringBuilder.toString();

    }

//    public static void main(String[] args) {
//        ExcelUtils.excelToCsv(null);
//    }
}
