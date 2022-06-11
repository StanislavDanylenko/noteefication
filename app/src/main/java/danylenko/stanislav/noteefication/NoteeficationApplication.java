package danylenko.stanislav.noteefication;

import android.app.Application;
import androidx.room.Room;

import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.util.db.NotesCache;

public class NoteeficationApplication extends Application {

    private static NoteeficationApplication instance;

    private AppDatabase database;
    private NotesCache notesCache;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        notesCache = new NotesCache();
    }

    public static NoteeficationApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public NotesCache getNotesCache() {
        return notesCache;
    }
}
