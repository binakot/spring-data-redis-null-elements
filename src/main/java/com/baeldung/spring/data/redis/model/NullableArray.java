package com.baeldung.spring.data.redis.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

@RedisHash("NullableArray")
public class NullableArray implements Serializable {

    private String id;
    private Integer[] values;

    public NullableArray(String id, Integer[] values) {
        this.id = id;
        this.values = values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer[] getValues() {
        return values;
    }

    public void setValues(Integer[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NullableArray.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("values=" + Arrays.toString(values))
                .toString();
    }
}
