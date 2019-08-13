package danylenko.stanislav.noteefication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.activity.NotesTabActivity;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.tab.NoteAdapter;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTES_LIST;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TAB_INDEX;


public class ActiveNotesPageFragment extends Fragment {

    private List<Note> notes;

    public static ActiveNotesPageFragment newInstance(ArrayList<Note> notes) {
        Bundle args = new Bundle();
        args.putSerializable(NOTES_LIST, notes);
        ActiveNotesPageFragment fragment = new ActiveNotesPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (List<Note>) getArguments().getSerializable(NOTES_LIST);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.fragment_active_notes, container, false);
        ListView listView = view.findViewById(R.id.listView);
        NoteAdapter noteAdapter = new NoteAdapter(getContext(), notes);
        listView.setAdapter(noteAdapter);

        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        //MenuItem item = menu.findItem(R.id.edit);
        //item.setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this.getContext(), "edit",  Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.getContext(), NotesTabActivity.class);
                intent.putExtra(TAB_INDEX, 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.delete:
                Toast.makeText(this.getContext(), "delete",  Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
