package com.danggn.challenge.common.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum FilePath {

    IMAGE("/image");

    private final String path;
}
