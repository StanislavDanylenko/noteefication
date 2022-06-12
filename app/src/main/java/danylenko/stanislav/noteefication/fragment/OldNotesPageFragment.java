package danylenko.stanislav.noteefication.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.tab.OnItemClickListener;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;
import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.tab.NoteAdapter;
import danylenko.stanislav.noteefication.util.db.NotesCache;


public class OldNotesPageFragment extends Fragment implements AppReceiver {

    private NoteAdapter noteAdapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_old_notes, container, false);
        RecyclerView listView = view.findViewById(R.id.listView);
        context = getContext();
        noteAdapter = new NoteAdapter(NotesCache.getInstance().getHistoryNotesList(), getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onMenuClick(Note item) {
                        showBottomSheetOldDialog(item);
                    }

                    @Override
                    public void onEmojiClick(Note item, TextView textView) {
                        // do nothing
                    }
                });
        listView.setAdapter(noteAdapter);

        listView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void showBottomSheetOldDialog(Note note) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.old_notes_bottom_menu);

        LinearLayout copy = bottomSheetDialog.findViewById(R.id.on_dialog_copy);
        LinearLayout restore = bottomSheetDialog.findViewById(R.id.on_dialog_restore);
        LinearLayout delete = bottomSheetDialog.findViewById(R.id.on_dialog_delete);

        copy.setOnClickListener(view -> {
            DBActionHandler.handleCopyAction(context, note.text);
            bottomSheetDialog.dismiss();
        });

        restore.setOnClickListener(view -> {
            DBActionHandler.handleRestoreAction(context, note);
            bottomSheetDialog.dismiss();
        });

        delete.setOnClickListener(view -> {
            DBActionHandler.handleDeleteAction(note);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    @Override
    public void receive() {
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public void register() {
        NotesCache.getInstance().registerReceiver(this);
    }

}
