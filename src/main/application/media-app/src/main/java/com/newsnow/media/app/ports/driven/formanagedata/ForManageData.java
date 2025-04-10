package com.newsnow.media.app.ports.driven.formanagedata;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.lib.reactive.UnitReactive;
import reactor.core.publisher.Mono;

public interface ForManageData {
    Mono<Task> getTaskById(Long id);
    Mono<Task> updateTask(Task task);
    Mono<Void> deleteTask(Long id);
    Mono<Task> patchTask(Task task);
    Mono<Task> createTask(Task task);
}
