package danylenko.stanislav.noteefication.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Active", "Old"};
    final int PAGE_COUNT = 2;

    private AppDatabase db;
    private NoteDao noteDao;

    public SampleFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return ActiveNotesPageFragment.newInstance((ArrayList<Note>) noteDao.getByStatus(Status.ACTUAL.getValue()));
        } else {
            return OldNotesPageFragment.newInstance((ArrayList<Note>) noteDao.getByStatus(Status.DONE.getValue()));
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
