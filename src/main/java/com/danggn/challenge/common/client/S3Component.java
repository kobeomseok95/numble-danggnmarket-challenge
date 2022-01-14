package com.danggn.challenge.common.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
class S3Component {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
}
