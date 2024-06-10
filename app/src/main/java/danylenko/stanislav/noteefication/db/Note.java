package danylenko.stanislav.noteefication.db;

import androidx.room.ColumnInfo;
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
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "smile")
    public String smile;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "status")
    @TypeConverters({StatusConverter.class})
    public Status status;

    @ColumnInfo(name = "creationDate")
    @TypeConverters({DateConverter.class})
    public Date creationDate;

    @ColumnInfo(name = "finishDate")
    @TypeConverters({DateConverter.class})
    public Date finishDate;

}
