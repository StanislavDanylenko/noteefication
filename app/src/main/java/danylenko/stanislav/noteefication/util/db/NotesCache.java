package danylenko.stanislav.noteefication.util.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

public final class NotesCache {

    private final NoteDao NOTE_DAO;

    private final List<Note> ACTIVE_NOTES;
    private final List<Note> HISTORY_NOTES;

    private final List<AppReceiver> APP_RECEIVERS = new ArrayList<>();


    public NotesCache() {
        NOTE_DAO = NoteeficationApplication.getInstance().getDatabase().noteDao();
        ACTIVE_NOTES = Collections.synchronizedList(new ArrayList<>());
        HISTORY_NOTES = Collections.synchronizedList(new ArrayList<>());
    }

    public void invalidateCaches() {
        ACTIVE_NOTES.clear();
        HISTORY_NOTES.clear();

        ACTIVE_NOTES.addAll(NOTE_DAO.getByStatus(Status.ACTUAL.getValue()));
        HISTORY_NOTES.addAll(NOTE_DAO.getByStatus(Status.DONE.getValue()));
    }

    public void updateBoth() {
        invalidateCaches();
        push();
    }

    public void updateListByNote(Note note) {
        /*if (note.status == Status.ACTUAL) {
            updateActive();
        } else {
            updateHistory();
        }
        push();*/
        updateBoth();
    }

    public void updateByStatus(Status status) {
        /*if (status == Status.ACTUAL) {
            updateActive();
        } else {
            updateHistory();
        }
        push();*/
        updateBoth();
    }

    private void updateActive() {
        List<Note> byStatus = NOTE_DAO.getByStatus(Status.ACTUAL.getValue());
        ACTIVE_NOTES.clear();
        ACTIVE_NOTES.addAll(byStatus);
    }

    private void updateHistory() {
        List<Note> byStatus = NOTE_DAO.getByStatus(Status.DONE.getValue());
        HISTORY_NOTES.clear();
        HISTORY_NOTES.addAll(byStatus);
    }

    public List<Note> getActiveNotesList() {
        return ACTIVE_NOTES;
    }

    public List<Note> getHistoryNotesList() {
        return HISTORY_NOTES;
    }

    public void registerReceiver(AppReceiver receiver) {
        APP_RECEIVERS.add(receiver);
    }

    private void push() {
        for (AppReceiver appReceiver : APP_RECEIVERS) {
            appReceiver.receive();
        }
    }
}
