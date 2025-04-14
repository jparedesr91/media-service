package com.newsnow.media.domain.ports.driven.task;

import com.newsnow.media.domain.model.Task;
import reactor.core.publisher.Mono;

public interface TaskRepositoryPort {
    Mono<Task> getById(String id);
    Mono<Task> save(Task task);
}
