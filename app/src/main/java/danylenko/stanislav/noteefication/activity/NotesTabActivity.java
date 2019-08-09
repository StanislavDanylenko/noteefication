package danylenko.stanislav.noteefication.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;
import danylenko.stanislav.noteefication.fragment.SampleFragmentPagerAdapter;


public class NotesTabActivity extends AppCompatActivity {

    private SampleFragmentPagerAdapter adapter;
    private AppDatabase db;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_tab);

        getSupportActionBar().hide();

        ViewPager viewPager = findViewById(R.id.viewpager);
        adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        int tabIndex = intent.getIntExtra("tabIndex", 0);

        viewPager.setCurrentItem(tabIndex);

    }

    public void cleanHistory(View view) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        noteDao.deleteByStatus(Status.DONE.getValue());
        recreateActivity(1);
    }

    public void cleanAllCurrent(View view) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        List<Note> actual = noteDao.getByStatus(Status.ACTUAL.getValue());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        for (Note note : actual) {
            notificationManager.cancel((int)note.id);
            note.status = Status.DONE;
            noteDao.update(note);
        }
        recreateActivity(0);
    }

    private void recreateActivity(int tabIndex) {
        finish();

        Intent selfIntent = getIntent();
        selfIntent.putExtra("tabIndex", tabIndex);
        startActivity(selfIntent);
    }
}
