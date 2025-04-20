package com.newsnow.media.app.ports.driven.message;

public interface MessageProvider {

  String msg(String code);
  String msg(String code, Object... args);

}
