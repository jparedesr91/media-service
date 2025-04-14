package com.newsnow.media.app;

import com.newsnow.media.app.annotations.Facade;
import com.newsnow.media.app.facade.TaskFacade;
import com.newsnow.media.app.usecases.CreateTaskUseCase;
import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.app.usecases.GetTaskUseCase;
import com.newsnow.media.domain.facade.Result;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driving.task.CreateTaskRequest;
import reactor.core.publisher.Mono;

@Facade("taskFacade")
public class TaskFacadeImpl implements TaskFacade {

    private final GetTaskUseCase getTask;
    private final CreateTaskUseCase createTask;

    public TaskFacadeImpl(GetTaskUseCase getTask, CreateTaskUseCase createTask) {
        this.getTask = getTask;
        this.createTask = createTask;
    }

    @Override
    public Mono<Result<Task>> getTask(String idTask, MediaServiceContext context) {
        return getTask.apply(idTask, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Task>> createTask(CreateTaskRequest createTaskRequest, MediaServiceContext context) {
        return createTask.apply(createTaskRequest, context).map(Result::successful);
    }

}
