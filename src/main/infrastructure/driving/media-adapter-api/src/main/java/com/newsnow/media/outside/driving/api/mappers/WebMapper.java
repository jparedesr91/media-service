package com.newsnow.media.outside.driving.api.mappers;

import com.newsnow.media.app.exceptions.errors.Error;
import com.newsnow.media.app.facade.config.Result;
import com.newsnow.media.outside.driving.api.ErrorDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO.ResponseStatusEnum;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class WebMapper {

  @Autowired
  private MapperRegistry mapperRegistry;

  @Mapping(target = "responseStatus", source = "responseStatus")
  @Mapping(target = "errors", expression = "java(toErrorDTO(result.getErrors()))")
  @Mapping(target = "data", expression = "java(mapData(result))")
  public abstract GenericResponseDTO toGenericResponseDTO(ResponseStatusEnum responseStatus, Result<?> result);

  @Mapping(target = "responseStatus", source = "responseStatus")
  @Mapping(target = "errors", expression = "java(toErrorDTO(errors))")
  @Mapping(target = "data", ignore = true)
  public abstract GenericResponseDTO toGenericResponseDTO(ResponseStatusEnum responseStatus, List<Error> errors);

  protected Object mapData(Result<?> result) {
    return mapperRegistry.map(result.getVal());
  }

  public abstract ErrorDTO toErrorDTO(Error val);
  public abstract List<ErrorDTO> toErrorDTO(List<Error> val);

}