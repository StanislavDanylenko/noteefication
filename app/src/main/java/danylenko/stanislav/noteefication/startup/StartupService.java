package danylenko.stanislav.noteefication.startup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.activity.MainActivity;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;
import danylenko.stanislav.noteefication.notification.NotificationUtils;

public class StartupService extends JobIntentService {

    private AppDatabase db;
    private NoteDao noteDao;

    public static final int JOB_ID = 0x01;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, StartupService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        List<Note> allTheNotes = noteDao.getByStatus(Status.ACTUAL.getValue());
        for(Note note : allTheNotes) {
            NotificationUtils.showNotification(this, note.text, new Intent(this, MainActivity.class), (int)note.id);
        }
    }


}
