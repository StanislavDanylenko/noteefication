package danylenko.stanislav.noteefication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.fragment.SampleFragmentPagerAdapter;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TAB_INDEX;


public class NotesTabActivity extends AppCompatActivity {

    private static boolean restart = false;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_tab);

        getSupportActionBar().hide();

        viewPager = findViewById(R.id.viewpager);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        int tabIndex = intent.getIntExtra(TAB_INDEX, 0);

        viewPager.setCurrentItem(tabIndex);

    }

    public void cleanHistory(View view) {
        DBActionHandler.handleCleanHistoryAction();
        recreateActivity();
    }

    public void cleanAllCurrent(View view) {
        DBActionHandler.handleAllCurrentAction(this);
        recreateActivity();
    }

    private void recreateActivity() {
        finish();

        Intent selfIntent = getIntent();
        int tabIndex = viewPager.getCurrentItem();
        selfIntent.putExtra(TAB_INDEX, tabIndex);
        startActivity(selfIntent);
    }

    public static boolean isRestart() {
        return restart;
    }

    public static void setRestart(boolean restart) {
        NotesTabActivity.restart = restart;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (restart) {
            restart = false;
            recreateActivity();
        }
    }
}
