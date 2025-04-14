package com.newsnow.media.app.facade;

import com.newsnow.media.app.TaskFacadeImpl;
import com.newsnow.media.domain.ports.driven.image.ImageProcessingPort;
import com.newsnow.media.domain.ports.driven.image.ImageStoragePort;
import com.newsnow.media.domain.ports.driven.task.TaskRepositoryPort;
import com.newsnow.media.domain.ports.driving.task.ManageTaskPort;

/**
 * Interface collecting all driving ports.
 * It allows getting an instance of the application from the driven ports.
 */
public interface TaskFacade extends ManageTaskPort {

}
