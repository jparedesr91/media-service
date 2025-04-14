package com.newsnow.media.outside.driven.adapter.image.processor;

import com.newsnow.media.domain.ports.driven.ImageProcessingPort;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ThumbnailatorAdapter implements ImageProcessingPort {

  @Override
  public Mono<ImageData> resize(ImageData image, int width, int height) {
    return Mono.fromCallable(() -> {
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          Thumbnails.of(new ByteArrayInputStream(image.bytes()))
              .size(width, height)
              .outputFormat("jpg")
              .toOutputStream(output);
          return new ImageData(
              output.toByteArray(),
              DigestUtils.md5DigestAsHex(output.toByteArray()),
              "image/jpeg"
          );
        })
        .subscribeOn(Schedulers.boundedElastic());
  }
}
