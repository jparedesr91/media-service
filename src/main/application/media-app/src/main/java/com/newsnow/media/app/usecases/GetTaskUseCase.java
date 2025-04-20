package com.newsnow.media.app.usecases;

import com.newsnow.media.app.exceptions.EntityNotFoundException;
import com.newsnow.media.app.facade.config.MediaServiceContext;
import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.message.MessageProvider;
import com.newsnow.media.app.ports.driven.task.TaskRepositoryPort;
import java.util.function.BiFunction;
import reactor.core.publisher.Mono;

public class GetTaskUseCase implements BiFunction<String, MediaServiceContext, Mono<Task>> {

    private final TaskRepositoryPort taskRepository;
    private final MessageProvider messageProvider;

    public GetTaskUseCase(TaskRepositoryPort taskRepository, MessageProvider messageProvider) {
        this.taskRepository = taskRepository;
      this.messageProvider = messageProvider;
    }

    @Override
    public Mono<Task> apply(String taskId, MediaServiceContext mediaServiceContext) {
        return taskRepository.getById(taskId)
            .switchIfEmpty(Mono.error(new EntityNotFoundException(messageProvider.msg(EntityNotFoundException.MESSAGE_CODE))));
    }
}
