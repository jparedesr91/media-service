package com.newsnow.media.outside.driven.adapter.persistence.adapters;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.task.TaskRepositoryPort;
import com.newsnow.media.outside.driven.adapter.persistence.mappers.PersistenceMapper;
import com.newsnow.media.outside.driven.adapter.persistence.repositories.ReactiveTaskRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TaskMongoAdapter implements TaskRepositoryPort {

  private final ReactiveTaskRepository reactiveTaskRepository;
  private final PersistenceMapper MAPPER;

  public TaskMongoAdapter(ReactiveTaskRepository reactiveTaskRepository, PersistenceMapper mapper) {
    this.reactiveTaskRepository = reactiveTaskRepository;
    MAPPER = mapper;
  }

  @Override
  public Mono<Task> getById(String id) {
    return reactiveTaskRepository.findById(id).map(MAPPER::toTask);
  }

  @Override
  public Mono<Task> save(Task task) {
    return reactiveTaskRepository.save(MAPPER.toTaskEntity(task)).map(MAPPER::toTask);
  }

}
