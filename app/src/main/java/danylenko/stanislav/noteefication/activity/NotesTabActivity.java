package danylenko.stanislav.noteefication.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Date;
import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;
import danylenko.stanislav.noteefication.fragment.SampleFragmentPagerAdapter;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TAB_INDEX;


public class NotesTabActivity extends AppCompatActivity {

    public static boolean restart = false;

    private AppDatabase db;
    private NoteDao noteDao;
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
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        noteDao.deleteByStatus(Status.DONE.getValue());
        recreateActivity();
    }

    public void cleanAllCurrent(View view) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        List<Note> actual = noteDao.getByStatus(Status.ACTUAL.getValue());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        for (Note note : actual) {
            notificationManager.cancel((int)note.id);
            note.status = Status.DONE;
            note.creationDate = new Date();
            noteDao.update(note);
        }
        recreateActivity();
    }

    private void recreateActivity() {
        finish();

        Intent selfIntent = getIntent();
        int tabIndex = viewPager.getCurrentItem();
        selfIntent.putExtra(TAB_INDEX, tabIndex);
        startActivity(selfIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(restart){
            restart = false;
            recreateActivity();
        }
    }
}
