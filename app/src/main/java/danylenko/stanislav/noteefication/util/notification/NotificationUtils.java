package danylenko.stanislav.noteefication.util.notification;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import java.util.List;
import java.util.Random;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.activity.NotesTabActivity;
import danylenko.stanislav.noteefication.handler.DeleteReceiver;
import danylenko.stanislav.noteefication.handler.CopyReceiver;
import danylenko.stanislav.noteefication.handler.EditReceiver;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_COPY;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_DELETE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.ACTION_EDIT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.CHANNEL_ID;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.COPY;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.REMOVE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT_TEXT;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EMOJI;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTE_APPLICATION_CHANNEL;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TYPE_NOTE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.VALUE;

public final class NotificationUtils {

    private NotificationUtils() {
    }

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


        Intent buttonIntent = new Intent(ACTION_DELETE, null, context, DeleteReceiver.class);
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
                .setOnlyAlertOnce(true)

                .addAction(R.drawable.ic_delete_black_24dp, REMOVE, btPendingIntent)
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


    public static void restartTabsActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        if (!runningTaskInfo.isEmpty()) {
            ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
            boolean equals = componentInfo.getClassName().equals(NotesTabActivity.class.getName());
            if (equals) {
                Intent intent = new Intent(context, NotesTabActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                NotesTabActivity.setRestart(true);
                context.startActivity(intent);
            }
        }
    }


}
