package com.newsnow.media.domain.ports.driving.formanagetask;

import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.facade.Result;
import com.newsnow.media.domain.model.Task;
import reactor.core.publisher.Mono;

public interface ForManageTaskPort {
    Mono<Result<Task>> getTask(String idTask, MediaServiceContext mediaServiceContext);
    Mono<Result<Task>> createTask(CreateTaskRequest createTask, MediaServiceContext mediaServiceContext);
}
