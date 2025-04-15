package com.newsnow.media.app.ports.driving.task;

import com.newsnow.media.app.facade.config.MediaServiceContext;
import com.newsnow.media.app.facade.config.Result;
import com.newsnow.media.app.domain.Task;
import reactor.core.publisher.Mono;

public interface ManageTaskPort {
    Mono<Result<Task>> getTask(String idTask, MediaServiceContext mediaServiceContext);
    Mono<Result<Task>> createTask(CreateTaskRequest createTask, MediaServiceContext mediaServiceContext);
}
