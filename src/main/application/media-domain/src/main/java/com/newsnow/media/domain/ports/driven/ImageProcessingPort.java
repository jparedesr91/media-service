package com.newsnow.media.domain.ports.driven;

import reactor.core.publisher.Mono;

public interface ImageProcessingPort {
  Mono<ImageData> resize(ImageData image, int width, int height);
  record ImageData(byte[] bytes, String md5, String contentType) {}
}
