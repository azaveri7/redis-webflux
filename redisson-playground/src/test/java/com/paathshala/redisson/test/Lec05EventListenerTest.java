package com.paathshala.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05EventListenerTest extends BaseTest {

    @Test
    public void expireEventTest() {
        RBucketReactive<String> bucket =
                this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("anand", 10, TimeUnit.SECONDS);
        // This indicates wherever get is called, it will execute System.out::printly
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        // Bucket can listen for events
        Mono<Void> monoEvent = bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String s) {
                System.out.println("Expired : " + s);
            }
        }).then();
        StepVerifier.create(set.concatWith(monoEvent))
                .verifyComplete();
        // extend expiry time
        sleep(11000);
    }
}
