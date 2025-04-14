package com.newsnow.media.outside.driven.adapter.persistence.mappers;

import com.newsnow.media.domain.model.Task;
import com.newsnow.media.outside.driven.adapter.persistence.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersistenceMapper {

  PersistenceMapper MAPPER = Mappers.getMapper(PersistenceMapper.class);

  TaskEntity toTaskEntity(Task task);
  Task toTask(TaskEntity taskEntity);

}
