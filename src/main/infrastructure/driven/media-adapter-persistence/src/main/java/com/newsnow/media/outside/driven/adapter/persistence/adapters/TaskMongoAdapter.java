package com.newsnow.media.outside.driven.adapter.persistence.adapters;

import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driven.task.TaskRepositoryPort;
import com.newsnow.media.outside.driven.adapter.persistence.repositories.ReactiveTaskRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import static com.newsnow.media.outside.driven.adapter.persistence.mappers.PersistenceMapper.MAPPER;

@Component
public class TaskMongoAdapter implements TaskRepositoryPort {

  private final ReactiveTaskRepository reactiveTaskRepository;

  public TaskMongoAdapter(ReactiveTaskRepository reactiveTaskRepository) {
    this.reactiveTaskRepository = reactiveTaskRepository;
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
