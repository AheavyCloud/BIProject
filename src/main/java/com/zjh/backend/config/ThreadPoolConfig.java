package com.zjh.backend.config;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

// 线程池配置
@Configuration
//@ConfigurationProperties(prefix = "threadpool.param")
@Data

public class ThreadPoolConfig {

    // 线程工厂实现创建线程
    ThreadFactory threadFactory = new ThreadFactory(){
        private int count = 1;
        @Override
        public Thread newThread(@NotNull Runnable r) {

            Thread thread = new Thread(r);
            thread.setName("线程" + count);
            count++;
            return thread;
        }
    };
    private int corePoolSize = 4;
    private int maximumPoolSize = 20;
    private Long keepAliveTime = 50L; // 非核心线程空闲存活时间
    private TimeUnit timeUnit = TimeUnit.SECONDS;  // 空闲时间单位
    // private BlockingDeque<Runnable> blockingDeque = ; // 阻塞队列，也是任务队列，从此任务队列中取线程数据 -- 结合实际情况配置
    // 生成线程池bean实例对象
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                new ArrayBlockingQueue<>(4),
                threadFactory
        );
        return threadPoolExecutor;
    }
//    CompletableFuture
}
