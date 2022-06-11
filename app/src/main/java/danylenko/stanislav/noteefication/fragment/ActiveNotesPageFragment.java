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

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.customreceiver.AppReceiver;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.tab.NoteAdapter;
import danylenko.stanislav.noteefication.tab.OnItemClickListener;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;
import danylenko.stanislav.noteefication.util.db.NotesCache;
import danylenko.stanislav.noteefication.util.modal.ModalUtils;
import danylenko.stanislav.noteefication.util.notification.NotificationUtils;


public class ActiveNotesPageFragment extends Fragment implements AppReceiver {

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
        context = getContext();
        noteAdapter = new NoteAdapter(NotesCache.getActiveNotesList(), context,
                new OnItemClickListener() {
                    @Override
                    public void onMenuClick(Note item) {
                        showBottomSheetActiveDialog(item);
                    }

                    @Override
                    public void onEmojiClick(Note item, TextView textView) {
                        String emoji = NotificationUtils.randomEmoji();
                        textView.setText(emoji);
                    }
                });
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
