package com.danggn.challenge.common.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QuerydslUtil {

    public static <T> boolean hasNextContents(List<T> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }

    public static <K, V> boolean containsKey(Map<K, V> transform, K key) {
        if (transform.containsKey(key)) {
            return true;
        }
        return false;
    }
}
