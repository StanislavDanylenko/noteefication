package danylenko.stanislav.noteefication.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import danylenko.stanislav.noteefication.util.notification.NotificationUtils;

@Database(entities = {Note.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Note ADD COLUMN smile TEXT NOT NULL DEFAULT '';");
            database.execSQL("ALTER TABLE Note ADD COLUMN finishDate INTEGER DEFAULT 0;");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("UPDATE Note SET smile = ?;", new Object[]{NotificationUtils.randomEmoji()});
            database.execSQL("UPDATE Note SET finishDate = creationDate, creationDate = 0 WHERE status = ?;", new Object[]{Status.DONE.getValue()});
        }
    };
}
