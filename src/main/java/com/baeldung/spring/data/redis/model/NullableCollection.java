package com.baeldung.spring.data.redis.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@RedisHash("NullableCollection")
public class NullableCollection implements Serializable {

    private String id;
    private List<Integer> values;

    public NullableCollection(String id, List<Integer> values) {
        this.id = id;
        this.values = values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NullableCollection.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("values=" + values)
                .toString();
    }
}
