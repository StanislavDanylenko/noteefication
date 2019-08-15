package danylenko.stanislav.noteefication.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import danylenko.stanislav.noteefication.db.converter.DateConverter;
import danylenko.stanislav.noteefication.db.converter.StatusConverter;

@Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String text;

    @TypeConverters({StatusConverter.class})
    public Status status;

    @TypeConverters({DateConverter.class})
    public Date creationDate;

}
