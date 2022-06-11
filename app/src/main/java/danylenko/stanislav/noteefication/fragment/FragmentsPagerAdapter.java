package danylenko.stanislav.noteefication.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import danylenko.stanislav.noteefication.NoteeficationApplication;

public class FragmentsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Active", "Old"};
    private static final int PAGE_COUNT = 2;

//    private final NoteDao noteDao;

    public FragmentsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        NoteeficationApplication.getInstance().getNotesCache().invalidateCaches();
//        noteDao = NoteeficationApplication.getInstance().getDatabase().noteDao();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ActiveNotesPageFragment(); /*ActiveNotesPageFragment.newInstance(noteDao.getByStatus(Status.ACTUAL.getValue()));*/
        } else {
            return new OldNotesPageFragment(); /*OldNotesPageFragment.newInstance(noteDao.getByStatus(Status.DONE.getValue()));*/
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
