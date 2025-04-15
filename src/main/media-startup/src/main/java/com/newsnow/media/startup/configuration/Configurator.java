package com.newsnow.media.startup.configuration;

import com.newsnow.media.app.TaskFacadeImpl;
import com.newsnow.media.app.facade.TaskFacade;
import com.newsnow.media.app.usecases.CreateTaskUseCase;
import com.newsnow.media.app.usecases.GetTaskUseCase;
import com.newsnow.media.app.usecases.TaskProcessorUseCase;
import com.newsnow.media.app.ports.driven.image.ImageProcessingPort;
import com.newsnow.media.app.ports.driven.image.ImageStoragePort;
import com.newsnow.media.app.ports.driven.task.TaskRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurator {

    @Bean
    TaskFacade TaskFacade(GetTaskUseCase getTaskUseCase, CreateTaskUseCase createTaskUseCase) {
        return new TaskFacadeImpl(getTaskUseCase, createTaskUseCase);
    }

    @Bean
    GetTaskUseCase getTaskUseCase(TaskRepositoryPort taskRepository) {
        return new GetTaskUseCase(taskRepository);
    }

    @Bean
    CreateTaskUseCase createTaskUseCase(TaskRepositoryPort taskRepository, TaskProcessorUseCase taskProcessor, ImageProcessingPort imageProcessing) {
        return new CreateTaskUseCase(taskRepository, taskProcessor, imageProcessing);
    }

    @Bean
    TaskProcessorUseCase taskProcessor(ImageProcessingPort imageProcessor, ImageStoragePort imageStorage, TaskRepositoryPort taskRepository) {
        return new TaskProcessorUseCase(imageProcessor, imageStorage, taskRepository);
    }

}
