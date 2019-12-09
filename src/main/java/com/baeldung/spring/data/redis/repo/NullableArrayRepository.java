package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.model.NullableArray;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NullableArrayRepository extends CrudRepository<NullableArray, String> {
}
