package danylenko.stanislav.noteefication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.tab.NoteAdapter;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.NOTES_LIST;


public class OldNotesPageFragment extends Fragment {

    private List<Note> notes;


    public static OldNotesPageFragment newInstance(ArrayList<Note> notes) {
        Bundle args = new Bundle();
        args.putSerializable(NOTES_LIST, notes);
        OldNotesPageFragment fragment = new OldNotesPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (List<Note>) getArguments().getSerializable(NOTES_LIST);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.fragment_old_notes, container, false);
        ListView listView = view.findViewById(R.id.listView);
        NoteAdapter noteAdapter = new NoteAdapter(getContext(), notes);
        listView.setAdapter(noteAdapter);
        return view;
    }

}
