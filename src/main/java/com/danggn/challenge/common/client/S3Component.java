package com.danggn.challenge.common.client;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "cloud.aws.s3")
class S3Component {

    private String bucket;
}
