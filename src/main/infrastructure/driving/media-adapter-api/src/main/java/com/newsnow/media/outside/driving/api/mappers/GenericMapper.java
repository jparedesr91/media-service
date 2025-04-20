package com.newsnow.media.outside.driving.api.mappers;

public abstract class GenericMapper <S, T> {
  public abstract T map(S source);
}
