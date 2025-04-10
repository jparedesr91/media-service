package com.newsnow.media.app.usecases;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.formanagedata.ForManageData;
import com.newsnow.media.lib.reactive.UnitReactive;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class GetTask implements Function<Long, Mono<Task>> {

    private final ForManageData forManageData;

    public GetTask(ForManageData forManageData) {
        this.forManageData = forManageData;
    }

    @Override
    public Mono<Task> apply(Long taskId) {
        return forManageData.getTaskById(taskId);
    }
}
