package com.zjh.backend.exception;

import com.zjh.backend.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {
    // 利用反射构造类
    // 1. 获取Class字节码文件
    @ExceptionHandler(BIException.class)
    public Result handleBIExcetion(BIException e){
      log.info("BIException:" + e.getMessage());
      return new Result(e);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e){
        log.info("RuntimeException:" + e.getMessage());
        return new Result(ErrorCode.SYSTEM_INNER_ERROR);
    }
}
