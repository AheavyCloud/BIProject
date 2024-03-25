package com.zjh.backend.rabbitmq.producter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MessageProducter {
    @Resource
    private RabbitTemplate rabbitTemplate;
    /**
     * @param exchange 交换机名称：指定交换机发送到哪个交换机
     * @param message 消息实体
     * @param routingKey 路由键：指定消息根据什么规则到达相应的队列
     * */
    public void sendMessage(String exchange, String routingKey, String message){
        // 将消息message发送到指定的交换机和路由键
        rabbitTemplate.convertAndSend(exchange, routingKey, message);

    }
}
