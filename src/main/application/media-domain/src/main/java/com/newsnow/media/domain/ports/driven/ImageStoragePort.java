package com.newsnow.media.domain.ports.driven;

import com.newsnow.media.domain.ports.driven.ImageProcessingPort.ImageData;
import reactor.core.publisher.Mono;

public interface ImageStoragePort {
    Mono<String> uploadImage(ImageData image);
}
