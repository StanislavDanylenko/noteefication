package danylenko.stanislav.noteefication.handler;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.COPIED_TO_CLIPBOARD;


public class CopyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getStringExtra(NoteeficationApplicationConstants.VALUE);

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", value);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, COPIED_TO_CLIPBOARD, Toast.LENGTH_LONG).show();

    }

}
