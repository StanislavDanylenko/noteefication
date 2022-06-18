package danylenko.stanislav.noteefication.util.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

public final class NotesCache {

    private static volatile NotesCache instance;

    private final NoteDao NOTE_DAO;

    private final List<Note> ACTIVE_NOTES;
    private final List<Note> HISTORY_NOTES;

    private final Map<Status, AppReceiver> RECEIVERS = new HashMap<>();

    public static NotesCache getInstance() {
        NotesCache result = instance;
        if (result != null) {
            return result;
        }
        synchronized(NotesCache.class) {
            if (instance == null) {
                instance = new NotesCache();
            }
            return instance;
        }
    }

    public NotesCache() {
        NOTE_DAO = NoteeficationApplication.getInstance().getDatabase().noteDao();
        ACTIVE_NOTES = new ArrayList<>();
        HISTORY_NOTES = new ArrayList<>();
    }

    public void invalidateCaches() {
        ACTIVE_NOTES.clear();
        HISTORY_NOTES.clear();

        ACTIVE_NOTES.addAll(NOTE_DAO.getByStatusActive(Status.ACTUAL.getValue()));
        HISTORY_NOTES.addAll(NOTE_DAO.getByStatusOld(Status.DONE.getValue()));
    }

    public void updateBoth() {
        invalidateCaches();
        pushActive();
        pushOld();
    }

    public void updateListByNote(Note note) {
        if (note.status == Status.ACTUAL) {
            updateActive();
            pushActive();
        } else {
            updateHistory();
            pushOld();
        }
    }

    public void updateByStatus(Status status) {
        if (status == Status.ACTUAL) {
            updateActive();
            pushActive();
        } else {
            updateHistory();
            pushOld();
        }
    }

    private void updateActive() {
        List<Note> byStatus = NOTE_DAO.getByStatusActive(Status.ACTUAL.getValue());
        ACTIVE_NOTES.clear();
        ACTIVE_NOTES.addAll(byStatus);
    }

    private void updateHistory() {
        List<Note> byStatus = NOTE_DAO.getByStatusOld(Status.DONE.getValue());
        HISTORY_NOTES.clear();
        HISTORY_NOTES.addAll(byStatus);
    }

    public List<Note> getActiveNotesList() {
        return ACTIVE_NOTES;
    }

    public List<Note> getHistoryNotesList() {
        return HISTORY_NOTES;
    }

    public void registerReceiver(AppReceiver receiver, Status status) {
        RECEIVERS.put(status, receiver);
    }

    private void pushActive() {
        AppReceiver actualReceiver = RECEIVERS.get(Status.ACTUAL);
        if (actualReceiver != null) {
            actualReceiver.receive();
        }
    }

    private void pushOld() {
        AppReceiver oldReceiver = RECEIVERS.get(Status.DONE);
        if (oldReceiver != null) {
            oldReceiver.receive();
        }
    }
}
