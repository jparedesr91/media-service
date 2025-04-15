package com.newsnow.media.app.ports.driven.task;

import com.newsnow.media.app.domain.Task;
import reactor.core.publisher.Mono;

public interface TaskRepositoryPort {
    Mono<Task> getById(String id);
    Mono<Task> save(Task task);
}
