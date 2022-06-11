package danylenko.stanislav.noteefication.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> noteList;
    private final Context context;
    private final OnItemClickListener listener;

    public NoteAdapter(List<Note> noteList, Context context, OnItemClickListener listener) {
        this.noteList = noteList;
        this.context = context;
        this.listener = listener;
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
        private TextView menuButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.value);
            date = (TextView) itemView.findViewById(R.id.date);
            smile = (TextView) itemView.findViewById(R.id.smile);
            menuButton = (TextView) itemView.findViewById(R.id.menu);
        }

        public void bind(final Note note, final OnItemClickListener listener) {
            smile.setText("\uD83D\uDE0E");

            value.setText(note.text);

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
            String dateString = dateFormat.format(note.creationDate);
            date.setText(dateString);

            menuButton.setOnClickListener(view -> listener.onItemClick(note));
        }
    }
}
