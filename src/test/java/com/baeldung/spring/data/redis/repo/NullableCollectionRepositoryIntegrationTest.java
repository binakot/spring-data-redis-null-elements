package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.config.RedisConfig;
import com.baeldung.spring.data.redis.model.NullableCollection;
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
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class NullableCollectionRepositoryIntegrationTest {

    @Autowired
    private NullableCollectionRepository nullableCollectionRepository;

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
        final NullableCollection collection = new NullableCollection("1", Arrays.asList(1, 2, 3, 4, 5));
        nullableCollectionRepository.save(collection);
        final Optional<NullableCollection> retrievedCollection = nullableCollectionRepository.findById("1");
        assertEquals(collection.getValues(), retrievedCollection.map(NullableCollection::getValues).orElse(null));
    }

    @Test
    public void allNulls() {
        final NullableCollection collection = new NullableCollection("1", Arrays.asList(null, null, null, null, null));
        nullableCollectionRepository.save(collection);
        final Optional<NullableCollection> retrievedCollection = nullableCollectionRepository.findById("1");
        assertEquals(collection.getValues(), retrievedCollection.map(NullableCollection::getValues).orElse(null));
        //Expected :[null, null, null, null, null]
        //Actual   :null
    }

    @Test
    public void leadingNull() {
        final NullableCollection collection = new NullableCollection("1", Arrays.asList(null, 2, 3, 4, 5));
        nullableCollectionRepository.save(collection);
        final Optional<NullableCollection> retrievedCollection = nullableCollectionRepository.findById("1");
        assertEquals(collection.getValues(), retrievedCollection.map(NullableCollection::getValues).orElse(null));
        //Expected :[null, 2, 3, 4, 5]
        //Actual   :null
    }

    @Test
    public void middleNull() {
        final NullableCollection collection = new NullableCollection("1", Arrays.asList(1, 2, null, 4, 5));
        nullableCollectionRepository.save(collection);
        final Optional<NullableCollection> retrievedCollection = nullableCollectionRepository.findById("1");
        assertEquals(collection.getValues(), retrievedCollection.map(NullableCollection::getValues).orElse(null));
        //Expected :[1, 2, null, 4, 5]
        //Actual   :[1, 2]
    }

    @Test
    public void endingNull() {
        final NullableCollection collection = new NullableCollection("1", Arrays.asList(1, 2, 3, 4, null));
        nullableCollectionRepository.save(collection);
        final Optional<NullableCollection> retrievedCollection = nullableCollectionRepository.findById("1");
        assertEquals(collection.getValues(), retrievedCollection.map(NullableCollection::getValues).orElse(null));
        //Expected :[1, 2, 3, 4, null]
        //Actual   :[1, 2, 3, 4]
    }
}