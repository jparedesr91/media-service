package com.newsnow.media.outside.driven.adapter.persistence.adapters;

import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driven.TaskRepositoryPort;
import com.newsnow.media.outside.driven.adapter.persistence.repositories.TaskRepository;
import reactor.core.publisher.Mono;
import static com.newsnow.media.outside.driven.adapter.persistence.mappers.PersistenceMapper.MAPPER;

public class TaskMongoAdapter implements TaskRepositoryPort {

  private final TaskRepository taskRepository;

  public TaskMongoAdapter(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public Mono<Task> getById(String id) {
    return taskRepository.findById(id).map(MAPPER::toTask);
  }

  @Override
  public Mono<Task> save(Task task) {
    return taskRepository.save(MAPPER.toTaskEntity(task)).map(MAPPER::toTask);
  }
}
