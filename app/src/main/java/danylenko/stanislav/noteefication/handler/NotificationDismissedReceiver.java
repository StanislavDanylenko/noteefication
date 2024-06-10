package danylenko.stanislav.noteefication.handler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;

public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int notificationId = extras.getInt(NOTIFICATION_ID);
                DBActionHandler.handleShowAction(context, notificationId);
            }
        }
    }
}
