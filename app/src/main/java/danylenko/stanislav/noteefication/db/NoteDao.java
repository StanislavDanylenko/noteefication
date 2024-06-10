package danylenko.stanislav.noteefication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAll();


    @Query("select last_insert_rowid()")
    int getLastId();

    @Query("SELECT * FROM Note WHERE id = :id")
    Note getById(long id);

    @Query("SELECT * FROM Note WHERE status = :status ORDER BY creationDate DESC")
    List<Note> getByStatusActive(String status);

    @Query("SELECT * FROM Note WHERE status = :status ORDER BY finishDate DESC")
    List<Note> getByStatusOld(String status);


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