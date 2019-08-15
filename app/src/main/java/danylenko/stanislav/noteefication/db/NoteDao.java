package danylenko.stanislav.noteefication.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAll();


    @Query("select last_insert_rowid()")
    int getLastId();

    @Query("SELECT * FROM Note WHERE id = :id")
    Note getById(long id);

    @Query("SELECT * FROM Note WHERE status = :status")
    List<Note> getByStatus(String status);


    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note WHERE status = :status")
    void deleteByStatus(String status);

    @Query("DELETE FROM Note")
    void deleteAll();

}