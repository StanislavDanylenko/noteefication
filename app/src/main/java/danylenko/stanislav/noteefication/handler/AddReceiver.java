package danylenko.stanislav.noteefication.handler;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTIFICATION_ID;

public class AddReceiver extends BroadcastReceiver {

    private AppDatabase db;
    private NoteDao noteDao;

    @Override
    public void onReceive(Context context, Intent intent) {

        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();

        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);

        Note note = noteDao.getById(notificationId);
        note.status = Status.DONE;
        note.creationDate = new Date();
        noteDao.update(note);

    }

}
