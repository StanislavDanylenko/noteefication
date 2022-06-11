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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.tab.NoteAdapter;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;
import danylenko.stanislav.noteefication.util.db.NotesCache;
import danylenko.stanislav.noteefication.util.modal.ModalUtils;


public class ActiveNotesPageFragment extends Fragment implements AppReceiver {

    private List<Note> notes;
    private NoteAdapter noteAdapter;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_active_notes, container, false);
        RecyclerView listView = view.findViewById(R.id.listView);
        notes = NotesCache.getActiveNotesList();
        context = getContext();
        noteAdapter = new NoteAdapter(notes, context, this::showBottomSheetActiveDialog);
        listView.setAdapter(noteAdapter);

        listView.setLayoutManager(new LinearLayoutManager(context));

        register();

        return view;
    }

    private void showBottomSheetActiveDialog(Note note) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.active_notes_bottom_menu);

        LinearLayout show = bottomSheetDialog.findViewById(R.id.an_dialog_show);
        LinearLayout copy = bottomSheetDialog.findViewById(R.id.an_dialog_copy);
        LinearLayout edit = bottomSheetDialog.findViewById(R.id.an_dialog_edit);
        LinearLayout remove = bottomSheetDialog.findViewById(R.id.an_dialog_remove);

        show.setOnClickListener(view -> {
            DBActionHandler.handleShowAction(context, note);
            bottomSheetDialog.dismiss();
        });

        copy.setOnClickListener(view -> {
            DBActionHandler.handleCopyAction(context, note.text);
            bottomSheetDialog.dismiss();
        });

        edit.setOnClickListener(view -> {
            ModalUtils.showDialog(context, note, getActivity().getIntent());
            bottomSheetDialog.dismiss();
        });

        remove.setOnClickListener(view -> {
            DBActionHandler.handleRemoveAction(context, note.id);
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
        NotesCache.registerReceiver(this);
    }
}
