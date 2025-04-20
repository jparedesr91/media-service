package com.newsnow.media.outside.driven.adapter.persistence.entities;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "task")
public class TaskEntity {

  @Id
  private String id;
  @CreatedDate
  private LocalDateTime createdAt;
  private MediaEntity newMedia;
  private MediaEntity oldMedia;
  private TaskStatusEntity status;
  private String statusDetail;

}
