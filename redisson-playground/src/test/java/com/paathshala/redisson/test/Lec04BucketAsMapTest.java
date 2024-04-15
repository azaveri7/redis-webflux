package com.paathshala.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec04BucketAsMapTest extends BaseTest {

    @Test
    public void bucketsAsMap() {
        // user:1:name
        // user:2:name
        // user:3:name

        // set user:1:name aadhya
        // set user:2:name anand
        // set user:3:name neha

        Mono<Void> monoMap = this.redissonReactiveClient.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name")
                .doOnNext(System.out::println)
                .then();

        StepVerifier.create(monoMap)
                .verifyComplete();

    }
}
