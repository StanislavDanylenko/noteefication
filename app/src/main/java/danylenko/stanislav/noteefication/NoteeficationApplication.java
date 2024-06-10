package danylenko.stanislav.noteefication;

import static danylenko.stanislav.noteefication.db.AppDatabase.MIGRATION_2_3;
import static danylenko.stanislav.noteefication.db.AppDatabase.MIGRATION_3_4;

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
                .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
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
