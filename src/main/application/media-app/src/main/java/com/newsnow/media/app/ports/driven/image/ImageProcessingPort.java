package com.newsnow.media.app.ports.driven.image;

import reactor.core.publisher.Mono;

public interface ImageProcessingPort {
  Mono<ImageData> resize(ImageData image, int width, int height);
  String commuteMD5(byte[] bytes);
  record ImageData(byte[] bytes, String md5, String contentType) {}
}
