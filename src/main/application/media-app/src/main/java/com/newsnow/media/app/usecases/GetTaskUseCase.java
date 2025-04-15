package com.newsnow.media.app.usecases;

import com.newsnow.media.app.facade.config.MediaServiceContext;
import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.task.TaskRepositoryPort;
import com.newsnow.media.domain.exceptions.EntityNotFoundException;
import java.util.function.BiFunction;
import reactor.core.publisher.Mono;

public class GetTaskUseCase implements BiFunction<String, MediaServiceContext, Mono<Task>> {

    private final TaskRepositoryPort taskRepository;

    public GetTaskUseCase(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> apply(String taskId, MediaServiceContext mediaServiceContext) {
        return taskRepository.getById(taskId)
            .switchIfEmpty(Mono.error(new EntityNotFoundException("seed.service.entity_not_found")));
    }
}
