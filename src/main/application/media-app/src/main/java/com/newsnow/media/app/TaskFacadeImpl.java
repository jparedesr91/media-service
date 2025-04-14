package com.newsnow.media.app;

import com.newsnow.media.app.facade.TaskFacade;
import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.app.usecases.GetTask;
import com.newsnow.media.app.usecases.TaskProcessor;
import com.newsnow.media.domain.facade.Result;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driven.ImageProcessingPort;
import com.newsnow.media.domain.ports.driven.ImageStoragePort;
import com.newsnow.media.domain.ports.driven.TaskRepositoryPort;
import com.newsnow.media.domain.ports.driving.formanagetask.CreateTaskRequest;
import reactor.core.publisher.Mono;

public class TaskFacadeImpl implements TaskFacade {

    private final TaskRepositoryPort taskRepository;
    private final ImageStoragePort imageStorage;
    private final ImageProcessingPort imageProcessing;

    public TaskFacadeImpl(TaskRepositoryPort taskRepository, ImageStoragePort imageStorage,
        ImageProcessingPort imageProcessing) {
        this.taskRepository = taskRepository;
        this.imageStorage = imageStorage;
      this.imageProcessing = imageProcessing;
    }

    @Override
    public Mono<Result<Task>> getTask(String idTask, MediaServiceContext context) {
        GetTask getTask = new GetTask(taskRepository);
        return getTask.apply(idTask, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Task>> createTask(CreateTaskRequest createTaskRequest, MediaServiceContext context) {
        com.newsnow.media.app.usecases.CreateTask createTask = new com.newsnow.media.app.usecases.CreateTask(taskRepository, new TaskProcessor(imageProcessing,imageStorage,taskRepository));
        return createTask.apply(createTaskRequest, context).map(Result::successful);
    }

}
