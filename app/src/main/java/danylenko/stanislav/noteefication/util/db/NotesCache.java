package danylenko.stanislav.noteefication.util.db;

import java.util.ArrayList;
import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

public final class NotesCache {

    private static final NoteDao NOTE_DAO = NoteeficationApplication.getInstance().getDatabase().noteDao();

    private NotesCache() {}

    private static final List<Note> ACTIVE_NOTES = new ArrayList<>();
    private static final List<Note> HISTORY_NOTES = new ArrayList<>();

    private static final List<AppReceiver> APP_RECEIVERS = new ArrayList<>();

    public static void invalidateCaches() {
        ACTIVE_NOTES.clear();
        HISTORY_NOTES.clear();

        ACTIVE_NOTES.addAll(NOTE_DAO.getByStatus(Status.ACTUAL.getValue()));
        HISTORY_NOTES.addAll(NOTE_DAO.getByStatus(Status.DONE.getValue()));
    }

    public static void updateListByNote(Note note) {
        /*if (note.status == Status.ACTUAL) {
            updateActive();
        } else {
            updateHistory();
        }*/
        invalidateCaches();
        push();
    }

    public static void updateByStatus(Status status) {
        /*if (status == Status.ACTUAL) {
            updateActive();
        } else {
            updateHistory();
        }*/
        invalidateCaches();
        push();
    }

    private static void updateActive() {
        List<Note> byStatus = NOTE_DAO.getByStatus(Status.ACTUAL.getValue());
        ACTIVE_NOTES.clear();
        ACTIVE_NOTES.addAll(byStatus);
    }

    private static void updateHistory() {
        List<Note> byStatus = NOTE_DAO.getByStatus(Status.DONE.getValue());
        HISTORY_NOTES.clear();
        HISTORY_NOTES.addAll(byStatus);
    }

    public static List<Note> getActiveNotesList() {
        return ACTIVE_NOTES;
    }

    public static List<Note> getHistoryNotesList() {
        return HISTORY_NOTES;
    }

    public static void registerReceiver(AppReceiver receiver) {
        APP_RECEIVERS.add(receiver);
    }

    private static void push() {
        for (AppReceiver appReceiver : APP_RECEIVERS) {
            appReceiver.receive();
        }
    }

}
