package com.newsnow.media.outside.driven.adapter.persistence.repositories;

import com.newsnow.media.outside.driven.adapter.persistence.entities.TaskEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveTaskRepository extends ReactiveMongoRepository<TaskEntity, String> {
}
