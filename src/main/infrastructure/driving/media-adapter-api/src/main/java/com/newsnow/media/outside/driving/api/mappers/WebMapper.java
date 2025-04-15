package com.newsnow.media.outside.driving.api.mappers;

import com.newsnow.media.app.exceptions.errors.Error;
import com.newsnow.media.app.facade.config.Result;
import com.newsnow.media.outside.driving.api.ErrorDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO.ResponseStatusEnum;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WebMapper {

  @Mapping(target = "responseStatus", source = "responseStatus")
  @Mapping(target = "errors", expression = "java(toErrorDTO(result.getErrors()))")
  @Mapping(target = "data", source = "result.val")
  GenericResponseDTO toGenericResponseDTO(ResponseStatusEnum responseStatus, Result<?> result);

  @Mapping(target = "responseStatus", source = "responseStatus")
  @Mapping(target = "errors", expression = "java(toErrorDTO(errors))")
  @Mapping(target = "data", ignore = true)
  GenericResponseDTO toGenericResponseDTO(ResponseStatusEnum responseStatus, List<Error> errors);

  ErrorDTO toErrorDTO(Error val);
  List<ErrorDTO> toErrorDTO(List<Error> val);

}

