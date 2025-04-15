package com.newsnow.media.outside.driven.adapter.image.processor;

import com.newsnow.media.app.ports.driven.image.ImageProcessingPort;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
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
                  commuteMD5(output.toByteArray()),
              "image/jpeg"
          );
        })
        .subscribeOn(Schedulers.boundedElastic());
  }

    @Override
    public String commuteMD5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
