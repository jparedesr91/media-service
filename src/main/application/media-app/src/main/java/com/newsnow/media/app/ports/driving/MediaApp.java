package com.newsnow.media.app.ports.driving;

import com.newsnow.media.app.BusinessLogic;
import com.newsnow.media.app.ports.driven.formanagedata.ForManageData;
import com.newsnow.media.app.ports.driven.formanagemedia.ForManageMedia;
import com.newsnow.media.app.ports.driven.forupdatearticle.ForUpdateArticle;
import com.newsnow.media.app.ports.driving.formanagetask.ForManageTask;

/**
 * Interface collecting all driving ports.
 * It allows to get an instance of the application from the driven ports.
 */
public interface MediaApp extends ForManageTask {

     static MediaApp getInstance(ForManageData dataRepository, ForManageMedia contentService, ForUpdateArticle articleService) {
        return new BusinessLogic(dataRepository, contentService, articleService);
    }

    public static MediaApp getInstance() {
        throw new RuntimeException("No test-double has been implemented for the Application(Hexagon)");
    }
}
