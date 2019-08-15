package danylenko.stanislav.noteefication.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Active", "Old"};
    private static final int PAGE_COUNT = 2;

    private NoteDao noteDao;

    public SampleFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        noteDao = NoteeficationApplication.getInstance().getDatabase().noteDao();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ActiveNotesPageFragment.newInstance(noteDao.getByStatus(Status.ACTUAL.getValue()));
        } else {
            return OldNotesPageFragment.newInstance(noteDao.getByStatus(Status.DONE.getValue()));
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
