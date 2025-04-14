package com.newsnow.media.app.usecases;

import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driven.task.TaskRepositoryPort;
import java.util.function.BiFunction;
import reactor.core.publisher.Mono;

public class GetTaskUseCase implements BiFunction<String, MediaServiceContext, Mono<Task>> {

    private final TaskRepositoryPort taskRepository;

    public GetTaskUseCase(TaskRepositoryPort taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Task> apply(String taskId, MediaServiceContext mediaServiceContext) {
        return taskRepository.getById(taskId);
    }
}
