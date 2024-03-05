package com.zjh.backend.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private Integer database;
    private String host;
    private Integer port;
    private String password;
    // 交给Bean容器管理， Spring启动时自动创建RedissonClient的对象
    // 利用Bean生成数据 这个方法名需要使用返回值类型的小驼峰命名法
    // redissonClient 是一个接口，通过create创建了这个服务器
    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        // redis 配置 单机模式
        config.useSingleServer()
                .setDatabase(database) // 数据库应该使业务数据库和限流数据库分开
                .setAddress("redis://" + host +":" + port);
//                .setPassword(password);
        // 利用Client创建redis客户端，然后返回
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
