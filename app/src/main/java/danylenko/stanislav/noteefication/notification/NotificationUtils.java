package danylenko.stanislav.noteefication.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.handler.AddReceiver;
import danylenko.stanislav.noteefication.handler.CopyReceiver;
import danylenko.stanislav.noteefication.handler.EditReceiver;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_COPY;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_DELETE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_EDIT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.CHANNEL_ID;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.COPY;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.DELETE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT_TEXT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EMOJI;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTE_APPLICATION_CHANNEL;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TYPE_NOTE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.VALUE;

public final class NotificationUtils {

    private NotificationUtils(){}

    public static void showNotification(Context context, String body, Intent intent, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = CHANNEL_ID;
        String channelName = NOTE_APPLICATION_CHANNEL;
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


        Intent buttonIntent = new Intent(ACTION_DELETE, null, context, AddReceiver.class);
        buttonIntent.putExtra(NOTIFICATION_ID, id);

        PendingIntent btPendingIntent = PendingIntent.getBroadcast(context, id, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent copyIntent = new Intent(ACTION_COPY, null, context, CopyReceiver.class);
        copyIntent.putExtra(VALUE, body);

        PendingIntent copyPendingIntent = PendingIntent.getBroadcast(context, id, copyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_pushpinblack1)
                .setContentTitle(randomEmoji())
                .setOngoing(true)
                .setAutoCancel(false)
                .setColor(ContextCompat.getColor(context, R.color.colorBlack))

                .addAction(R.drawable.ic_delete_black_24dp, DELETE, btPendingIntent)
                .addAction(R.drawable.ic_content_copy, COPY, copyPendingIntent)

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setContentText(body);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Intent editIntent = new Intent(ACTION_EDIT, null, context, EditReceiver.class);
            editIntent.setAction(ACTION_EDIT);
            editIntent.putExtra(NOTIFICATION_ID, id);

            PendingIntent replyPendingIntent =
                    PendingIntent.getBroadcast(context, id, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            RemoteInput remoteInput = new RemoteInput.Builder(EDIT_TEXT)
                    .setLabel(TYPE_NOTE)
                    .build();

            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send,
                            EDIT, replyPendingIntent)
                            .addRemoteInput(remoteInput)
                            .build();

            mBuilder.addAction(action);
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.pushpinsm));
        } else {
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.mipmap.ic_launcher_pin));
        }


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(id, mBuilder.build());
    }

    private static String randomEmoji() {
        int position = new Random().nextInt(EMOJI.length);
        return EMOJI[position];
    }

}
