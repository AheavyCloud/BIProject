package com.zjh.backend.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiManagerTest {

    @Autowired
    private AiManager aiManager;
    @Test
    void dochat() {
        String answer = aiManager.dochat(1659171950288818178L,"日期，用户数据\n"
            + "1, 20\n"
                +"2, 30\n"
                +"3, 40"
        );
        System.out.println(answer);
    }
}