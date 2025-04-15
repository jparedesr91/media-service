package com.newsnow.media.app.ports.driven.image;

import com.newsnow.media.app.ports.driven.image.ImageProcessingPort.ImageData;
import reactor.core.publisher.Mono;

public interface ImageStoragePort {
    Mono<String> uploadImage(ImageData image);
}
