package com.newsnow.media.outside.driving.api.mappers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
public class MapperRegistry {

  private final Map<Class<?>, GenericMapper<?, ?>> mappers = new ConcurrentHashMap<>();

  @Autowired
  public MapperRegistry(List<GenericMapper<?, ?>> mapperBeans) {
    for (GenericMapper<?, ?> mapper : mapperBeans) {
      Class<?> currentClass = mapper.getClass();
      ParameterizedType genericMapperType = null;

      // Traverse superclasses to find the ParameterizedType
      while (currentClass != null && genericMapperType == null) {
        Type genericSuperclass = currentClass.getGenericSuperclass();

        if (genericSuperclass instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) genericSuperclass;
          Type rawType = pt.getRawType();

          // Check if the raw type is DomainMapper
          if (rawType instanceof Class && GenericMapper.class.isAssignableFrom((Class<?>) rawType)) {
            genericMapperType = pt;
          }
        }

        currentClass = currentClass.getSuperclass();
      }

      if (genericMapperType != null) {
        Type[] typeArgs = genericMapperType.getActualTypeArguments();
        if (typeArgs.length >= 2) {
          Class<?> sourceType = (Class<?>) typeArgs[0];
          mappers.put(sourceType, mapper);
        }
      }
    }
  }

  public <S, T> T map(S source) {
    if (source == null) return null;
    Class<?> sourceType = source.getClass();
    GenericMapper<S, T> mapper = (GenericMapper<S, T>) mappers.get(sourceType);
    return mapper != null ? mapper.map(source) : (T) source;
  }

}
