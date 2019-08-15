package danylenko.stanislav.noteefication.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_EDIT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT_TEXT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;


public class EditReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_EDIT.equals(intent.getAction())) {
            Bundle results = RemoteInput.getResultsFromIntent(intent);
            if (results != null) {
                CharSequence editedText = results.getCharSequence(EDIT_TEXT);
                if (editedText != null && editedText.length() > 0) {
                    int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
                    DBActionHandler.handleEditAction(context, notificationId, editedText.toString(), intent);
                }
            }
        }
    }

}
