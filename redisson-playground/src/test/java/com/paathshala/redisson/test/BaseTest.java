package com.paathshala.redisson.test;

import com.paathshala.redisson.test.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    private final RedissonConfig redissonConfig = new RedissonConfig();

    protected RedissonReactiveClient redissonReactiveClient;

    @BeforeAll
    public void setClient() {
        this.redissonReactiveClient = this.redissonConfig.getReactiveClient();
    }

    @AfterAll
    public void shutDown() {
        this.redissonReactiveClient.shutdown();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
