package com.newsnow.media.outside.driving.api.mappers;

import com.newsnow.media.domain.exceptions.errors.Error;
import com.newsnow.media.domain.facade.Result;
import com.newsnow.media.outside.driving.api.ErrorDTO;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WebMapper {
  WebMapper MAPPER = Mappers.getMapper(WebMapper.class);

  @Mapping(target = "responseStatus", source = "responseStatus")
  @Mapping(target = "errors", expression = "java(toErrorDTO(result.getErrors()))")
  @Mapping(target = "data", source = "result.val")
  GenericResponseDTO toGenericResponseDTO(String responseStatus, Result<?> result);

  ErrorDTO toErrorDTO(Error val);
  List<ErrorDTO> toErrorDTO(List<Error> val);

}

