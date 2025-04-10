package com.newsnow.media.app;

import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.formanagedata.ForManageData;
import com.newsnow.media.app.ports.driven.formanagemedia.ForManageMedia;
import com.newsnow.media.app.ports.driven.forupdatearticle.ForUpdateArticle;
import com.newsnow.media.app.ports.driving.MediaApp;
import com.newsnow.media.app.ports.driving.formanagetask.CreateTaskRequest;
import com.newsnow.media.app.usecases.GetTask;
import com.newsnow.media.app.usecases.ResizeMedia;
import com.newsnow.media.lib.reactive.UnitReactive;

public class BusinessLogic implements MediaApp {

    private final ForManageData dataRepository;
    private final ForManageMedia contentService;
    private final ForUpdateArticle articleService;

    public BusinessLogic(ForManageData dataRepository, ForManageMedia contentService, ForUpdateArticle articleService) {
        this.dataRepository = dataRepository;
        this.contentService = contentService;
        this.articleService = articleService;
    }

    @Override
    public UnitReactive<ForCreateTaskResponse> resizeMedia(CreateTaskRequest forResizeMediaRequest) {
        ResizeMedia resizeMedia = new ResizeMedia(dataRepository, contentService, articleService);
        return resizeMedia.apply(forResizeMediaRequest);
    }

    @Override
    public UnitReactive<Task> getTask(Long taskId) {
        GetTask getTask = new GetTask(dataRepository);
        return getTask.apply(taskId);
    }

}
