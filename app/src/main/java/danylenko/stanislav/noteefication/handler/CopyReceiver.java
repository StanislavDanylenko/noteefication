package danylenko.stanislav.noteefication.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import danylenko.stanislav.noteefication.util.db.DBActionHandler;
import danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants;


public class CopyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getStringExtra(NoteeficationApplicationConstants.VALUE);
        DBActionHandler.handleCopyAction(context, value);
    }

}
