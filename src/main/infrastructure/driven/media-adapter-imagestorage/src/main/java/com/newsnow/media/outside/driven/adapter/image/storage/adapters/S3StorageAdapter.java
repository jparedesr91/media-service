package com.newsnow.media.outside.driven.adapter.image.storage.adapters;


import com.newsnow.media.domain.ports.driven.ImageProcessingPort.ImageData;
import com.newsnow.media.domain.ports.driven.ImageStoragePort;
import java.util.UUID;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class S3StorageAdapter implements ImageStoragePort {

  private final S3AsyncClient s3Client;
  private final String bucketName;

  public S3StorageAdapter(S3AsyncClient s3Client, String bucketName) {
    this.s3Client = s3Client;
    this.bucketName = bucketName;
  }

  @Override
  public Mono<String> uploadImage(ImageData image) {
    String key = UUID.randomUUID() + ".jpg";
    PutObjectRequest request = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .contentType(image.contentType())
        .build();

    return Mono.fromFuture(s3Client.putObject(request, AsyncRequestBody.fromBytes(image.bytes())))
        .map(response -> buildS3Url(bucketName, key));
  }

  private String buildS3Url(String bucket, String key) {
    return "https://%s.s3.amazonaws.com/%s".formatted(bucket, key);
  }

}
