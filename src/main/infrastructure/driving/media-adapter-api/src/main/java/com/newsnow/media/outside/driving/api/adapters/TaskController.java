package com.newsnow.media.outside.driving.api.adapters;

import com.newsnow.media.app.facade.TaskFacade;
import com.newsnow.media.domain.ports.driving.task.CreateTaskRequest;
import com.newsnow.media.outside.driving.api.GenericResponseDTO;
import java.util.UUID;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TaskController extends Controller<TaskFacade> implements TaskApi {

  @Override
  public Mono<ResponseEntity<GenericResponseDTO>> createTask(Flux<Part> file, Integer width,
      Integer height, ServerWebExchange exchange) {

    return this.perform((facade, context) -> file.next()
        .flatMap(filePart -> DataBufferUtils.join(filePart.content()))
        .map(dataBuffer -> {
          byte[] bytes = new byte[dataBuffer.readableByteCount()];
          dataBuffer.read(bytes);
          DataBufferUtils.release(dataBuffer);
          return bytes;
        })
        .flatMap(bytes -> facade.createTask(new CreateTaskRequest(bytes, width, height), context)), exchange);
  }

  @Override
  public Mono<ResponseEntity<GenericResponseDTO>> getTask(UUID taskId,
      ServerWebExchange exchange) {
    return this.perform((facade, context) -> facade.getTask(taskId.toString(), context), exchange);
  }

}
