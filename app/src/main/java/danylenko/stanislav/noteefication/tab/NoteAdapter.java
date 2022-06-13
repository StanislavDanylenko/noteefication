package danylenko.stanislav.noteefication.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> noteList;
    private final OnItemClickListener listener;
    private final DateFormat dateFormat;

    public NoteAdapter(List<Note> noteList, Context context, OnItemClickListener listener) {
        this.noteList = noteList;
        this.listener = listener;
        this.dateFormat = android.text.format.DateFormat.getDateFormat(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(noteList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    protected class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView value;
        private TextView date;
        private TextView smile;
        private ImageButton menuButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            value = itemView.findViewById(R.id.value);
            date = itemView.findViewById(R.id.date);
            smile = itemView.findViewById(R.id.smile);
            menuButton = itemView.findViewById(R.id.menu);
        }

        public void bind(final Note note, final OnItemClickListener listener) {
            smile.setText(note.smile);
            value.setText(note.text);

            String creationDateString = getDateString(note.creationDate, "sometime");
            String finishDateString = getDateString(note.finishDate, "now");

            date.setText(creationDateString + " - " + finishDateString);

            menuButton.setOnClickListener(view -> listener.onMenuClick(note));
            smile.setOnClickListener(view -> listener.onEmojiClick(note, smile));
        }

        private String getDateString(Date value, String defaultValue) {
            if (value.getTime() != 0) {
                return dateFormat.format(value);
            } else {
                return defaultValue;
            }
        }
    }
}
