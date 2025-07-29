package danylenko.stanislav.noteefication.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import danylenko.stanislav.noteefication.util.db.NotesCache;

public class FragmentsPagerAdapter extends FragmentStateAdapter {

    private static final String[] TAB_TITLES = new String[]{"ACTIVE", "OLD"};

    public FragmentsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        NotesCache.getInstance().invalidateCaches();
    }

    @Override
    public int getItemCount() {
        return TAB_TITLES.length;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ActiveNotesPageFragment();
        } else {
            return new OldNotesPageFragment();
        }
    }

    public String getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
