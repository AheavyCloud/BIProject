package com.zjh.backend.manager;

import com.zjh.backend.exception.BIException;
import com.zjh.backend.exception.ErrorCode;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisLimiterManager {

    // 获取刚刚通过Bean注解生成的RedissionClient客户端类！
    @Resource
    private RedissonClient redissonClient;
    /**
     * @param key 利用key区分限流器，比如不同的用户ID， 不同的用户ID分别进行统计
     *       要求每个用户。每秒钟只能访问5次请求
     * */

    public void doRateLimitted(String key){

        // 利用redisson创建限流器
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // 限流器使用 , 设置限流策略
        /**
         * overall 所有的服务器同步计数Total rate for all RateLimiter instances
         *  速度为2：每个时间单位允许访问几次 时间间隔为1 时间单位为Second
         * */
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);

        // 每当一个请求来临时，请求几个令牌
        // 每次请求1个令牌，那么1秒种令牌为5，那么可以请求5次
        // 每秒请求5个令牌，那么1秒中令牌总数为5，那么一秒种最多可以请求1次！
        boolean canOp = rateLimiter.tryAcquire(1);

        // 如果没有令牌则抛出异常,如果抢不到令牌则抛出异常！
        if(!canOp)
            throw new BIException(ErrorCode.TOO_MANY_REQUEST);

    }
}
