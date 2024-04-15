package com.paathshala.redisson.test;

import com.paathshala.redisson.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class Lec02KeyValueObjectTest extends BaseTest{

    @Test
    public void keyValueObjectTest() {
        Student student = new Student("anand", 10, "rajkot", Arrays.asList(1, 50, 33));
        RBucketReactive<Student> bucket =
                this.redissonReactiveClient.getBucket("student:1",
                        new TypedJsonJacksonCodec(Student.class));
        Mono<Void> set = bucket.set(student);
        Mono<Void> getStudentMono = bucket.get()
                .doOnNext(System.out::println)
                .then();
        StepVerifier.create(set.concatWith(getStudentMono))
                .verifyComplete();
    }
}
