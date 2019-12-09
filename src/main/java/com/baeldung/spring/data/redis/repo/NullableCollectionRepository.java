package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.model.NullableCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NullableCollectionRepository extends CrudRepository<NullableCollection, String> {
}
