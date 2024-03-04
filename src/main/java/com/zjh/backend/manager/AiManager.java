package com.zjh.backend.manager;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;
    public String dochat(Long modelId, String message){
        DevChatRequest devChatRequest = new DevChatRequest();
//        devChatRequest.setModelId(1764295913696088065L);
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        System.out.println(response);
        if(response == null)
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "AI相应错误");
        return response.getData().getContent();
    }
}
