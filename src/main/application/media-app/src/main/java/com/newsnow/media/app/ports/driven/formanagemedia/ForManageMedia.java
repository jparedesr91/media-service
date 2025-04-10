package com.newsnow.media.app.ports.driven.formanagemedia;

import com.newsnow.media.app.domain.Media;
import com.newsnow.media.lib.reactive.UnitReactive;
import reactor.core.publisher.Mono;

public interface ForManageMedia {
    Mono<byte[]> downloadImage(String url);
    Mono<String> uploadImage(byte[] image);
}
