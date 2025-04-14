package com.newsnow.media.outside.driven.adapter.persistence.repositories;

import com.newsnow.media.outside.driven.adapter.persistence.entities.TaskEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<TaskEntity, String> {
}
