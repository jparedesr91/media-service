package com.newsnow.media.outside.driving.api.mappers;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.outside.driving.api.TaskDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TaskMapper extends GenericMapper<Task, TaskDataDTO> {


  @Override
  @Mapping(target = "originalMD5", source = "task.oldMedia.md5")
  @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd-HH:mm:ss")
  @Mapping(target = "resolution", expression = "java(String.valueOf(task.getNewMedia().getWidth()) + 'x' + String.valueOf(task.getNewMedia().getHeight()))")
  @Mapping(target = "storageUrl", source = "task.newMedia.url")
  public abstract TaskDataDTO map(Task task);

}
