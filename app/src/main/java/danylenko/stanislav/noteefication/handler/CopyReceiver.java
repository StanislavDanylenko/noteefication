package danylenko.stanislav.noteefication.handler;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class CopyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String value = intent.getStringExtra("value");

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", value);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show();

    }

}
