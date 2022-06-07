package danylenko.stanislav.noteefication.db.converter;

import androidx.room.TypeConverter;

import danylenko.stanislav.noteefication.db.Status;

public class StatusConverter {

    @TypeConverter
    public String fromStatus(Status status) {
        return status.getValue();
    }

    @TypeConverter
    public Status toStatus(String data) {
        return Status.fromString(data);
    }

}
