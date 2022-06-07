package danylenko.stanislav.noteefication;

import android.app.Application;
import androidx.room.Room;

import danylenko.stanislav.noteefication.db.AppDatabase;

public class NoteeficationApplication extends Application {

    private static NoteeficationApplication instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static NoteeficationApplication getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }




}
