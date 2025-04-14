package com.newsnow.media.app.facade;

import com.newsnow.media.app.TaskFacadeImpl;
import com.newsnow.media.domain.ports.driven.ImageProcessingPort;
import com.newsnow.media.domain.ports.driven.ImageStoragePort;
import com.newsnow.media.domain.ports.driven.TaskRepositoryPort;
import com.newsnow.media.domain.ports.driving.formanagetask.ForManageTaskPort;


/**
 * Interface collecting all driving ports.
 * It allows getting an instance of the application from the driven ports.
 */
public interface TaskFacade extends ForManageTaskPort {
     static TaskFacade getInstance(TaskRepositoryPort taskRepository, ImageStoragePort imageStorage, ImageProcessingPort imageProcessing) {
        return new TaskFacadeImpl(taskRepository, imageStorage, imageProcessing);
    }

     static TaskFacade getInstance() {
        throw new RuntimeException("No test-double has been implemented for the Application(Hexagon)");
    }
}
