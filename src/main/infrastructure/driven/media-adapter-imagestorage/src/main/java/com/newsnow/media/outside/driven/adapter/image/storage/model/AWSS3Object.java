package com.newsnow.media.outside.driven.adapter.image.storage.model;

import java.time.Instant;

public record AWSS3Object(String key, Instant lastModified, String eTag, Long size) {}