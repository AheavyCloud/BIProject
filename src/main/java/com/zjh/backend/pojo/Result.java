package com.zjh.backend.pojo;

import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private String description;
    private Object data;
    public final static Result success;
    static {
        success = new Result(200, "ok", "成功", null);
    }

    public Result(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.description = errorCode.getDescription();
    }

    public Result(BIException e){
        this.code = e.getCode();
        this.msg = e.getMessage();
        this.description = e.getDescription();
    }

    public static Result success(Object data){
        return new Result(200, "ok", "", data);
    }

}
