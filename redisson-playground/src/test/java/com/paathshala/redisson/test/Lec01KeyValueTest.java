package com.paathshala.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec01KeyValueTest extends BaseTest {

    @Test
    public void keyValueAccessTest() {
        RBucketReactive<String> bucket =
                this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("anand");
        // This indicates wherever get is called, it will execute System.out::printly
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();

    }

    @Test
    public void keyValueExpiryTest() {
        RBucketReactive<String> bucket =
                this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("anand", 10, TimeUnit.SECONDS);
        // This indicates wherever get is called, it will execute System.out::printly
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();

    }

    @Test
    public void keyValueExtendExpiryTest() {
        RBucketReactive<String> bucket =
                this.redissonReactiveClient.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("anand", 10, TimeUnit.SECONDS);
        // This indicates wherever get is called, it will execute System.out::printly
        Mono<Void> get = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(get))
                .verifyComplete();
        // extend expiry time
        sleep(5000);
        Mono<Boolean> expireMono =
                bucket.expire(60, TimeUnit.SECONDS);
        StepVerifier.create(expireMono)
                .expectNext(true)
                .verifyComplete();
        // access expiration time
        Mono<Void> ttlMono = bucket.remainTimeToLive()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(ttlMono)
                .verifyComplete();

    }
}
