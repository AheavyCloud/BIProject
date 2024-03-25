package com.zjh.backend.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.zjh.backend.constant.CommonConstant;
import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import com.zjh.backend.manager.AiManager;
import com.zjh.backend.pojo.entity.Charts;
import com.zjh.backend.rabbitmq.BIMQConstant;
import com.zjh.backend.service.ChartsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class MessageConsumer implements BIMQConstant {

    @Resource
    private ChartsService chartsService;

    @Resource
    private AiManager aiManager;
    // 使用RabbitListener注解指定要监听的队列名称为code_queue,设置消息确认机制为手动确认
    @RabbitListener(queues = BI_QUEUE_NAME, ackMode = "MANUAL")
    // @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag是方法参数注解，用于从消息头中获取投递标签deliveryTag
    // 在RabbitMQ中每条消息都会分配一个唯一的投递标签，用于标识该消息在通道中的投递状态和顺序，
    // 通过上述注解可以从消息头中取出该投递标签，并将其赋值非long delivery
    public void receiverMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {

        log.info("receiver message message={}", message);
        if(StringUtils.isBlank(message)){
            // 如果更新失败则拒绝当前消息，让消息重新进入队列
            channel.basicNack(deliveryTag, false, false);
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "无法获取消息···");
        }
        long chartId = Long.parseLong(message);
        Charts charts = chartsService.getById(chartId);
        if(charts == null){
            // 如果图表为空则拒绝消息且抛出异常
            channel.basicNack(deliveryTag, false, false);
            throw new BIException(ErrorCode.PARAM_ERROR, "图表信息为空···");
        }



        // 建议给任务的执行添加超时时间
        // 先修改任务状态为执行中
        Charts updateChart = new Charts();
        updateChart.setId(charts.getId());
        updateChart.setStatus("executing");

        boolean updateFlag = chartsService.updateById(updateChart);
        if(!updateFlag) {
            channel.basicNack(deliveryTag, false, false);
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "文件状态更新失败 --> chartId: " + updateChart.getId());
        }

       String aiResult = aiManager.dochat(CommonConstant.ID_MODEL, bUildUserInput(charts));
        String[] split = aiResult.split("【【【【【");

        if(split.length < 3)
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "AI生成错误");
        String genCode = split[1].trim(); // .trim将多余的空格和换行去掉
        String genChat = split[2].trim();

        // 执行AI模型成功：
        updateChart.setStatus("succeed");
        updateChart.setGenResult(genCode);
        updateChart.setGenChart(genChat);
        updateFlag = chartsService.updateById(updateChart);
        if(!updateFlag)
            throw new BIException(ErrorCode.SYSTEM_INNER_ERROR, "文件状态更新失败 --> chartId: " + updateChart.getId());



        // 通过此方法，可以告知RabbitMQ消息已经成功处理，可以进行确认和从队列中删除此消息
        channel.basicAck(deliveryTag, false);

    }

    private String bUildUserInput(Charts charts){
        StringBuilder userInData = new StringBuilder();
        userInData.append("分析目标" + charts.getGoal()).append("\n");
        userInData.append("请生成" + charts.getChartType()).append("\n");
        userInData.append("原始数据：").append(charts.getChartData()).append("\n");
        return userInData.toString();
    }
}
