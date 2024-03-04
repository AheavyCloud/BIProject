package com.zjh.backend.controller;

import cn.hutool.core.io.FileUtil;
import com.zjh.backend.common.BaseResponse;
import com.zjh.backend.common.ResultUtils;
import com.zjh.backend.constant.FileConstant;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.manager.CosManager;
import com.zjh.backend.model.dto.file.UploadFileRequest;
import com.zjh.backend.model.enums.FileUploadBizEnum;
import com.zjh.backend.pojo.Result;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartAddRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartDeleteRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartEditRequest;
import com.zjh.backend.model.dto.requestbody_.chartrequest.ChartQuaryRequest;
import com.zjh.backend.service.ChartsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chart") // api统一前缀 -- chart
public class ChartController {

    @Autowired
    ChartsService chartsService;
//    @Autowired
//    private CosManager cosManager;

    // 增加图表信息
    @PostMapping("/add")
    public Result addChartInfo(@RequestBody ChartAddRequest chartInfo){

        if(chartInfo == null)
            throw new BIException(ErrorCode.PARAM_ERROR);
        // 调用service数据插入表
        Long chartId = chartsService.doAddChart(chartInfo);
        return Result.success(chartId);
    }

    @PostMapping("/Delete")
    public Result deleteChart(@RequestBody ChartDeleteRequest chartDelRequest){

        if(chartDelRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 调用service层doDeleteChart方法
        chartsService.doDeleteChart(chartDelRequest);

        return Result.success;
    }

    @PostMapping("/edit")
    public Result editChartInfo(@RequestBody ChartEditRequest chartEditRequest){
        if (chartEditRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 执行service层数据
        Integer editFlag = chartsService.doEditChart(chartEditRequest);

        return Result.success(editFlag.toString());
    }

    @PostMapping("quary")
    public Result quaryChartInfo(@RequestBody ChartQuaryRequest chartQuaryRequest){
        if (chartQuaryRequest == null)
            throw new BIException(ErrorCode.PARAM_ERROR);

        // 执行service层数据查询
        List<Charts> charts = chartsService.doQuaryChartInfo(chartQuaryRequest);

        return Result.success(charts);

    }

//    /**
//     * 文件上传
//     *
//     * @param multipartFile
//     * @param uploadFileRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/upload")             // 注解uploadRequest 用户传入的参数
//    public BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile multipartFile,
//                                           UploadFileRequest uploadFileRequest, HttpServletRequest request) {
//        String biz = uploadFileRequest.getBiz();
//        FileUploadBizEnum fileUploadBizEnum = FileUploadBizEnum.getEnumByValue(biz);
//        if (fileUploadBizEnum == null) {
//            throw new BIException(ErrorCode.PARAM_ERROR);
//        }
//        validFile(multipartFile, fileUploadBizEnum);
//        // todo 校验登录用户
////        User loginUser = userService.getLoginUser(request);
//        // 文件目录：根据业务、用户来划分
//        String uuid = RandomStringUtils.randomAlphanumeric(8);
//        String filename = uuid + "-" + multipartFile.getOriginalFilename();
//        // todo 填入用户id
////        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), loginUser.getId(), filename);
//        String filepath = String.format("/%s/%s/%s", fileUploadBizEnum.getValue(), filename);
//        File file = null;
//        try {
//            // 上传文件
//            file = File.createTempFile(filepath, null);
//            multipartFile.transferTo(file);
//            cosManager.putObject(filepath, file);
//            // 返回可访问地址
//            return ResultUtils.success(FileConstant.COS_HOST + filepath);
//        } catch (Exception e) {
//            log.error("file upload error, filepath = " + filepath, e);
//            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "上传失败");
//        } finally {
//            if (file != null) {
//                // 删除临时文件
//                boolean delete = file.delete();
//                if (!delete) {
//                    log.error("file delete error, filepath = {}", filepath);
//                }
//            }
//        }
//    }
//
//    /**
//     * 校验文件
//     *
//     * @param multipartFile
//     * @param fileUploadBizEnum 业务类型
//     *                          todo 文件重构
//     */
//    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
//        // 文件大小
//        long fileSize = multipartFile.getSize();
//        // 文件后缀
//        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
//        final long ONE_M = 1024 * 1024L;
//        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
//            if (fileSize > ONE_M) {
//                throw new BIException(ErrorCode.PARAM_ERROR, "文件大小不能超过 1M");
//            }
//            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
//                throw new BIException(ErrorCode.PARAM_ERROR, "文件类型错误");
//            }
//        }
//    }

}
