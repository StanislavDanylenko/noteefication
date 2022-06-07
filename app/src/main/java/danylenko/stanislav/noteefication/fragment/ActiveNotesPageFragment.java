package danylenko.stanislav.noteefication.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

/*    public static ActiveNotesPageFragment newInstance(List<Note> notes) {
        Bundle args = new Bundle();
        args.putSerializable(NOTES_LIST, (ArrayList<Note>)notes);
        ActiveNotesPageFragment fragment = new ActiveNotesPageFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            notes = (List<Note>) getArguments().getSerializable(NOTES_LIST);
        }*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_active_notes, container, false);
        ListView listView = view.findViewById(R.id.listView);
        notes = NotesCache.getActiveNotesList();
        noteAdapter = new NoteAdapter(getContext(), notes);
        listView.setAdapter(noteAdapter);

        registerForContextMenu(listView);

        register();

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_active, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Note note = notes.get(info.position);
            switch (item.getItemId()) {
                case R.id.copy:
                    DBActionHandler.handleCopyAction(getContext(), note.text);
                    return true;
                case R.id.edit:
                    ModalUtils.showDialog(getContext(), note, getActivity().getIntent());
                    return true;
                case R.id.remove:
                    DBActionHandler.handleRemoveAction(getContext(), note.id);
                    return true;
                case R.id.show:
                    DBActionHandler.handleShowAction(getContext(), note);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        return super.onContextItemSelected(item);
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
