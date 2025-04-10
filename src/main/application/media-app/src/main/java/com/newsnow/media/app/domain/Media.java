package com.newsnow.media.app.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Media {
    private Long id;
    private String md5;
    private int width;
    private int height;
    private String url;
}
