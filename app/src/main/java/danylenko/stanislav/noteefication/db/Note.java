package danylenko.stanislav.noteefication.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import danylenko.stanislav.noteefication.db.converter.DateConverter;
import danylenko.stanislav.noteefication.db.converter.StatusConverter;

@Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String smile;

    public String text;

    @TypeConverters({StatusConverter.class})
    public Status status;

    @TypeConverters({DateConverter.class})
    public Date creationDate;

    @TypeConverters({DateConverter.class})
    public Date finishDate;

}
