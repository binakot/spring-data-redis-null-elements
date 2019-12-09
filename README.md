## Spring Data Redis

The sample project to show a problem of storing collections with `null` elements in Spring Redis Data.

When we save the java nullable object as `null`, Redis just skips it, because it hasn't an equivalent for `NULL`.
On saving collections with some nulls, method will be break on the first null element in a collection.
It's okay for NULL-ending collections, but we're missing elements with NULL-leading or NULL in a middle collections.

Line of the code that break write elements on the first null:
https://github.com/spring-projects/spring-data-redis/blob/master/src/main/java/org/springframework/data/redis/core/convert/MappingRedisConverter.java#L705

Commit that adding this feature:
https://github.com/spring-projects/spring-data-redis/commit/2492fbe332b7565e0724b8f7ec6c62f5d3178b25#diff-b71f4f4366952781ed005f6224ff7d5dR511

Project is based on: 
https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-redis

### Relevant Articles:
- [Introduction to Spring Data Redis](http://www.baeldung.com/spring-data-redis-tutorial)
- [PubSub Messaging with Spring Data Redis](http://www.baeldung.com/spring-data-redis-pub-sub)
- [An Introduction to Spring Data Redis Reactive](https://www.baeldung.com/spring-data-redis-reactive)

### Build the Project with Tests Running
```
mvn clean install
```

### Run Tests Directly
```
mvn test
```
