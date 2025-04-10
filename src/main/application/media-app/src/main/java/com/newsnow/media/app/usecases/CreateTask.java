package com.newsnow.media.app.usecases;

import com.newsnow.media.app.domain.Media;
import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.formanagedata.ForManageData;
import com.newsnow.media.app.ports.driven.formanagemedia.ForManageMedia;
import com.newsnow.media.app.ports.driving.formanagetask.CreateTaskRequest;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.function.Function;

public class CreateTask implements Function<CreateTaskRequest, Mono<Task>> {

    private final ForManageData forManageData;
    private final ForManageMedia forManageMedia;

    public CreateTask(ForManageData forManageData, ForManageMedia forManageMedia) {
        this.forManageData = forManageData;
        this.forManageMedia = forManageMedia;
    }

    @Override
    public Mono<Task> apply(CreateTaskRequest createTaskRequest) {
       return Mono.just(createTaskRequest)
               .<Media>handle((taskRequest, sink) -> {
                   try {
                       sink.next(createMedia(taskRequest.getImage()));
                   } catch (Exception e) {
                       sink.error(new RuntimeException(e));
                   }
               })
               .flatMap(forManageMedia::uploadImage)
               .map(media ->  Task.builder()
                       .status("PENDING")
                       .oldMedia(media)
                       .newMedia(Media.builder()
                               .height(createTaskRequest.getHeight())
                               .width(createTaskRequest.getWidth())
                               .build())
                       .build())
               .flatMap(forManageData::createTask);
    }

    private Media createMedia(byte[] image) throws Exception {
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage buf = null;
        try {
            buf = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Media.builder().width(buf.getWidth()).height(buf.getHeight()).md5(computeMD5(image)).build();
    }


    private String computeMD5(byte[] image) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (InputStream is = new ByteArrayInputStream(image)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : md.digest()) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}