package com.newsnow.media.outside.driven.adapter.image.storage.adapters;

import com.newsnow.media.app.ports.driven.image.ImageProcessingPort.ImageData;
import com.newsnow.media.app.ports.driven.image.ImageStoragePort;
import java.net.URL;
import java.util.UUID;
import com.newsnow.media.outside.driven.adapter.image.storage.configuration.AwsProperties;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3StorageAdapter implements ImageStoragePort {

  private final S3AsyncClient s3Client;
  private final AwsProperties s3ConfigProperties;

  public S3StorageAdapter(S3AsyncClient s3Client, AwsProperties s3ConfigProperties) {
    this.s3Client = s3Client;
    this.s3ConfigProperties = s3ConfigProperties;
  }

  @Override
  public Mono<String> uploadImage(ImageData image) {
    String key = UUID.randomUUID() + ".jpg";
    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(s3ConfigProperties.getS3BucketName())
        .key(key)
        .contentType(image.contentType())
        .build();

    return Mono.fromFuture(s3Client.putObject(request, AsyncRequestBody.fromBytes(image.bytes())))
        .map(response -> s3Client.utilities()
                .getUrl(r -> r.bucket(s3ConfigProperties.getS3BucketName()).key(key)))
        .map(URL::toString);
  }

}
