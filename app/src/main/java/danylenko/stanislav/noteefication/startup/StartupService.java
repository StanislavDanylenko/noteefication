package danylenko.stanislav.noteefication.startup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;

public class StartupService extends JobIntentService {

    public static final int JOB_ID = 0x01;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, StartupService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        DBActionHandler.handleShowAllCurrentAction(this);
    }


}
