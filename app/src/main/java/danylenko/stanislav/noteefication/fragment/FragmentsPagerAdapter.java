package danylenko.stanislav.noteefication.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import danylenko.stanislav.noteefication.util.db.NotesCache;

public class FragmentsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Active", "Old"};
    private static final int PAGE_COUNT = 2;


    public FragmentsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        NotesCache.getInstance().invalidateCaches();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ActiveNotesPageFragment();
        } else {
            return new OldNotesPageFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
