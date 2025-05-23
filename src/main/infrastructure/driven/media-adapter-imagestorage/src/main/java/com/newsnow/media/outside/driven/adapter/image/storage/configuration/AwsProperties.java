package com.newsnow.media.outside.driven.adapter.image.storage.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws", ignoreUnknownFields = false)
public class AwsProperties {

    /**
     *  Aws access key ID
     */
    private String accessKey;


    /**
     *  Aws secret access key
     */
    private String secretKey;

    /**
     *  Aws region
     */
    private String region;
    /**
     *  Aws S3 bucket name
     */
    private String s3BucketName;

    /**
     * AWS S3 requires that file parts must have at least 5MB, except for the last part.
     */
    private int multipartMinPartSize;

    /**
     * S3 endpoint url
     */
    private String endpoint;
}