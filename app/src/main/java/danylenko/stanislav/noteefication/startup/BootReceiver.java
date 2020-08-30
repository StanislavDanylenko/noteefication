package danylenko.stanislav.noteefication.startup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                DBActionHandler.handleShowAllCurrentAction(context);
            } else {
                StartupService.enqueueWork(context, new Intent());
            }
        }
    }
}
