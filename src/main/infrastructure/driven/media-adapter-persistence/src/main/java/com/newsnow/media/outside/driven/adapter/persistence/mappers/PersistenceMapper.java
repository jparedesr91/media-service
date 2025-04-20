package com.newsnow.media.outside.driven.adapter.persistence.mappers;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.outside.driven.adapter.persistence.entities.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

  TaskEntity toTaskEntity(Task task);
  Task toTask(TaskEntity taskEntity);

}
