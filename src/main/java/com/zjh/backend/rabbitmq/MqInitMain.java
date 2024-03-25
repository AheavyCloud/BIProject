package com.zjh.backend.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class MqInitMain implements BIMQConstant{
    // 用于对RabbitMQ进行测试
    public static void main(String[] args) {

        try {
            // 创建连接工厂
            ConnectionFactory connectionFactory = new ConnectionFactory();
            // 创建链接
            Connection connection1 = connectionFactory.newConnection();
            // 创建通道
            Channel channel = connection1.createChannel();

            // 声明交换机类型为direct
            channel.exchangeDeclare(BI_EXCHANGE_NAME, "direct");

            // 创建队列，分配队列名称

            // 声明队列，设置队列持久化，非独占性，非自动删除
            channel.queueDeclare(BI_QUEUE_NAME, true, false, false, null);
            // 将队列绑定到交换机：指定routingKey为：“bi_routing_key”
            channel.queueBind(BI_QUEUE_NAME, BI_EXCHANGE_NAME, BI_ROUTING_KEY);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {

        }
    }
}
