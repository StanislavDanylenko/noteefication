package danylenko.stanislav.noteefication.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;

public class DeleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        DBActionHandler.handleRemoveAction(context, notificationId);
    }

}
