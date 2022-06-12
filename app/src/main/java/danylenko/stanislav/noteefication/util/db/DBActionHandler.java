package danylenko.stanislav.noteefication.util.db;

import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.activity.MainActivity;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;
import danylenko.stanislav.noteefication.util.notification.NotificationUtils;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.COPIED_TO_CLIPBOARD;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EMOJI;

public final class DBActionHandler {

    private static NoteDao noteDao = NoteeficationApplication.getInstance().getDatabase().noteDao();
    private static NotesCache notesCache = NotesCache.getInstance();

    private DBActionHandler() {}


    public static void handleEditAction(Context context, int notificationId, String editedText, Intent intent) {
        Note note = noteDao.getById(notificationId);
        note.text = editedText;

        noteDao.update(note);
        notesCache.updateListByNote(note);

        NotificationUtils.showNotification(context, editedText, note.smile, intent, notificationId);
    }

    public static void handleSmileAction(Context context, int notificationId, String smile, Intent intent) {
        Note note = noteDao.getById(notificationId);
        note.smile = smile;

        noteDao.update(note);
        notesCache.updateListByNote(note);

        NotificationUtils.showNotification(context, note.text, note.smile, intent, notificationId);
    }

    public static void handleAddAction(Context context, String value, Intent intent) {
        Note note = new Note();
        note.creationDate = new Date();
        note.finishDate = new Date(0);
        note.text = value;
        note.status = Status.ACTUAL;
        note.smile = NotificationUtils.randomEmoji();

        int id = (int) noteDao.insert(note);
        notesCache.updateListByNote(note);

        NotificationUtils.showNotification(context, value, note.smile, intent, id);
    }

    public static void handleCopyAction(Context context, String value) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", value);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, COPIED_TO_CLIPBOARD, Toast.LENGTH_LONG).show();
    }

    public static void handleRemoveAction(Context context, int notificationId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);

        Note note = noteDao.getById(notificationId);
        note.status = Status.DONE;
        note.finishDate = new Date();

        noteDao.update(note);
        notesCache.updateBoth();
    }

    public static void handleDeleteAction(Note note) {
        noteDao.delete(note);
        notesCache.updateListByNote(note);
    }

    public static void handleCleanHistoryAction() {
        noteDao.deleteByStatus(Status.DONE.getValue());
        notesCache.updateByStatus(Status.DONE);
    }

    public static void handleAllCurrentAction(Context context) {
        List<Note> actual = noteDao.getByStatus(Status.ACTUAL.getValue());

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        for (Note note : actual) {
            notificationManager.cancel(note.id);
            note.status = Status.DONE;
            note.creationDate = new Date();
            noteDao.update(note);
        }
        notesCache.updateBoth();
    }

    public static void handleShowAllCurrentAction(Context context) {
        List<Note> allTheNotes = noteDao.getByStatus(Status.ACTUAL.getValue());
        for (Note note : allTheNotes) {
            NotificationUtils.showNotification(context, note.text, note.smile, new Intent(context, MainActivity.class), note.id);
        }
    }

    public static void handleShowAction(Context context, Note note) {
        NotificationUtils.showNotification(context, note.text, note.smile, new Intent(context, MainActivity.class), note.id);
    }

    public static void handleRestoreAction(Context context, Note note) {
        note.creationDate = new Date();
        note.status = Status.ACTUAL;
        note.finishDate = new Date(0);

        noteDao.update(note);
        notesCache.updateBoth();

        NotificationUtils.showNotification(context, note.text, note.smile, new Intent(context, MainActivity.class), note.id);
    }
}
