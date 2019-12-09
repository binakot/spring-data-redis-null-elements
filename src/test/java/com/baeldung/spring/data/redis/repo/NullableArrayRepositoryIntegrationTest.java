package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.config.RedisConfig;
import com.baeldung.spring.data.redis.model.NullableArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.embedded.RedisServerBuilder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class NullableArrayRepositoryIntegrationTest {

    @Autowired
    private NullableArrayRepository nullableArrayRepository;

    private static redis.embedded.RedisServer redisServer;

    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 128M").build();
        redisServer.start();
    }

    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }

    @Test
    public void nonNulls() {
        final NullableArray array = new NullableArray("1", new Integer[]{1, 2, 3, 4, 5});
        nullableArrayRepository.save(array);
        final Optional<NullableArray> retrievedCollection = nullableArrayRepository.findById("1");
        assertArrayEquals(array.getValues(), retrievedCollection.map(NullableArray::getValues).orElse(null));
    }

    @Test
    public void allNulls() {
        final NullableArray array = new NullableArray("1", new Integer[]{null, null, null, null, null});
        nullableArrayRepository.save(array);
        final Optional<NullableArray> retrievedCollection = nullableArrayRepository.findById("1");
        assertArrayEquals(array.getValues(), retrievedCollection.map(NullableArray::getValues).orElse(null));
        // actual array was null
    }

    @Test
    public void leadingNull() {
        final NullableArray array = new NullableArray("1", new Integer[]{null, 2, 3, 4, 5});
        nullableArrayRepository.save(array);
        final Optional<NullableArray> retrievedCollection = nullableArrayRepository.findById("1");
        assertArrayEquals(array.getValues(), retrievedCollection.map(NullableArray::getValues).orElse(null));
        // actual array was null
    }

    @Test
    public void middleNull() {
        final NullableArray array = new NullableArray("1", new Integer[]{1, 2, null, 4, 5});
        nullableArrayRepository.save(array);
        final Optional<NullableArray> retrievedCollection = nullableArrayRepository.findById("1");
        assertArrayEquals(array.getValues(), retrievedCollection.map(NullableArray::getValues).orElse(null));
        // array lengths differed, expected.length=5 actual.length=2
    }

    @Test
    public void endingNull() {
        final NullableArray array = new NullableArray("1", new Integer[]{1, 2, 3, 4, null});
        nullableArrayRepository.save(array);
        final Optional<NullableArray> retrievedCollection = nullableArrayRepository.findById("1");
        assertArrayEquals(array.getValues(), retrievedCollection.map(NullableArray::getValues).orElse(null));
        // array lengths differed, expected.length=5 actual.length=4
    }
}