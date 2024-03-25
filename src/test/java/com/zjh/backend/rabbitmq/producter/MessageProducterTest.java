package com.zjh.backend.rabbitmq.producter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageProducterTest {

    @Resource
    private MessageProducter messageProducter;
    @Test
    void sendMessage() {
        messageProducter.sendMessage("code_exchange", "code_routing_key", "你好整个世界");

    }
}