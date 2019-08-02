package danylenko.stanislav.noteefication.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import android.support.design.widget.TabLayout;

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
    }

    public void cleanHistory(View view) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        noteDao.deleteByStatus(Status.DONE.getValue());
        Toast.makeText(this, "History was cleaned", Toast.LENGTH_LONG).show();
    }

    public void cleanAllCurrent(View view) {
        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();
        List<Note> actual = noteDao.getByStatus(Status.ACTUAL.getValue());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        for (Note note : actual) {
            notificationManager.cancel((int)note.id);
            noteDao.delete(note);
        }

        Toast.makeText(this, "Actual notes were cleaned", Toast.LENGTH_LONG).show();
    }
}
