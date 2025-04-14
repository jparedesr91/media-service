package com.newsnow.media.domain.ports.driven.image;

import com.newsnow.media.domain.ports.driven.image.ImageProcessingPort.ImageData;
import reactor.core.publisher.Mono;

public interface ImageStoragePort {
    Mono<String> uploadImage(ImageData image);
}
