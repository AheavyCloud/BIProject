package com.zjh.backend.controller;


import java.io.File;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;


import cn.hutool.core.io.FileUtil;
import com.zjh.backend.common.BaseResponse;
import com.zjh.backend.common.ResultUtils;
import com.zjh.backend.constant.FileConstant;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.manager.AiManager;
import com.zjh.backend.manager.CosManager;
import com.zjh.backend.manager.RedisLimiterManager;
import com.zjh.backend.model.dto.file.UploadFileRequest;
import com.zjh.backend.model.dto.reponsebody_.BiResponse;
import com.zjh.backend.model.dto.requestbody_.chartrequest.GenChartByAiRequest;
import com.zjh.backend.model.enums.FileUploadBizEnum;
import com.zjh.backend.pojo.Result;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.pojo.entity.User;
import com.zjh.backend.security_.JwtProvider;
import com.zjh.backend.service.ChartsService;
import com.zjh.backend.service.FileService;
import com.zjh.backend.service.UserService;
import com.zjh.backend.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/file")
@Slf4j
// todo 使用AOP对用户登录请求进行校验 因为每一个请求都需要对用户数据进行校验
public class FileController {

//    @Resource
//    private UserService userService;
//
//    @Resource
//    private CosManager cosManager;
    @Resource
    private AiManager aiManager;
    @Autowired
    private ChartsService chartsService;

    @Autowired
    private FileService fileService;

    @Resource
    private RedisLimiterManager redisLimiterManager;
    private final long idModel = 1659171950288818178L;
    private final long FILE_MAX_SIZE = 1024 * 1024; // 1M

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param genChartByAiRequest // 获取图表输入请求
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestPart("file") MultipartFile multipartFile,
                                     GenChartByAiRequest genChartByAiRequest, HttpServletRequest request) {

        // todo 用户登录校验，通过requestjwt获取登录信息，根据登录信息获取用户ID，将生成的图表信息传入后端数据库
        // 用户登录校验

        User user = JwtProvider.parseJWT(request); // id userAccount userRole

        String name = genChartByAiRequest.getName();
        String goal = genChartByAiRequest.getGoal();
        String chartType = genChartByAiRequest.getChartType();

         // 校验 不为空
        if(StringUtils.isBlank(goal))
            throw new BIException(ErrorCode.PARAM_ERROR, "目标值为空");
        if(StringUtils.isBlank(chartType))
            throw new BIException(ErrorCode.PARAM_ERROR, "数据类型为空");

        // 文件安全性校验 + 文件大小校验
        long fileSize = multipartFile.getSize();
        if(fileSize > FILE_MAX_SIZE)
            throw new BIException(ErrorCode.PARAM_ERROR, "文件内容超过1M");
        // 校验名称后缀
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null)
            throw new BIException(ErrorCode.PARAM_ERROR, "文件名为空");
        if(!originalFilename.contains("."))
            throw new BIException(ErrorCode.PARAM_ERROR, "文件名后缀出错");
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        if(!fileSuffix.equals("xlsx"))
            throw new BIException(ErrorCode.PARAM_ERROR, "文件格式错误");


        String result = ExcelUtils.excelToCsv(multipartFile);
        // 对用户输入进行封装 -- 系统预设

        StringBuilder userInData = new StringBuilder();
        userInData.append("分析目标" + goal).append("\n");
        userInData.append("请生成" + chartType).append("\n");
        userInData.append("原始数据：").append(result).append("\n");

        // 对用户调用此方法进行限流操作
        redisLimiterManager.doRateLimitted("userGenAi" + user.getId().toString());
        String aiResult = aiManager.dochat(idModel, userInData.toString());
        String[] split = aiResult.split("【【【【【");

        if(split.length < 3)
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "AI生成错误");
        String genCode = split[1].trim(); // .trim将多余的空格和换行去掉
        String genChat = split[2].trim();


        BiResponse biResponse = new BiResponse(genChat, genCode);

        // 存储数据 --> 根据用户id创建新的chart表存储用户数据
        // 获取了  name goal chartData chartType genChart genResult  userId

        Charts genChartData = new Charts();
        genChartData.setName(name);
        genChartData.setGoal(goal);
        genChartData.setChartData(result);
        genChartData.setChartType(chartType);
        genChartData.setGenChart(genChat);
        genChartData.setGenResult(genCode);
        genChartData.setUserId(user.getId());

        // 封装数据
        chartsService.save(genChartData);
        // 对方法进行限流

        // 获取图片id根据


        return Result.success(biResponse);


    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param fileUploadBizEnum 业务类型
     */
    private void validFile(MultipartFile multipartFile, FileUploadBizEnum fileUploadBizEnum) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadBizEnum.USER_AVATAR.equals(fileUploadBizEnum)) {
            if (fileSize > ONE_M) {
                throw new BIException(ErrorCode.PARAM_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BIException(ErrorCode.PARAM_ERROR, "文件类型错误");
            }
        }
    }
}
