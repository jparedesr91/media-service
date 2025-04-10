package com.newsnow.media.app.ports.driving.formanagetask;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.lib.reactive.UnitReactive;
import reactor.core.publisher.Mono;

public interface ForManageTask {

    Mono<Task> getTask(Long idTask);
    Mono<Task> createTask(CreateTaskRequest createTaskRequest);

}
