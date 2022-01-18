package com.danggn.challenge.common.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SliceUtil {

    public static <T> boolean hasNextContents(List<T> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
