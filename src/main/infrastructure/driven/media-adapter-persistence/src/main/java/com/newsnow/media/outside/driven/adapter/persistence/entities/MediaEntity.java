package com.newsnow.media.outside.driven.adapter.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "media")
public class MediaEntity {

  @Id
  private String id;
  private String md5;
  private int width;
  private int height;
  private String url;

}
