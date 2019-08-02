package danylenko.stanislav.noteefication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import danylenko.stanislav.noteefication.NoteeficationApplication;
import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.AppDatabase;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.db.NoteDao;
import danylenko.stanislav.noteefication.db.Status;
import danylenko.stanislav.noteefication.notification.NotificationUtils;


public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    private AppDatabase db;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = NoteeficationApplication.getInstance().getDatabase();
        noteDao = db.noteDao();

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

    }


    public void process(View view) {
        String value = editText.getText().toString();
        if(value.length() > 0) {
            Note note = new Note();
            note.creationDate = new Date();
            note.text = value;
            note.status = Status.ACTUAL;

            long id = noteDao.insert(note);

            NotificationUtils.showNotification(this, value, getIntent(), (int)id);
            editText.setText("");
        }


    }

    public void goToList(View view) {
        Intent goToListIntent = new Intent(this, NotesTabActivity.class);
        startActivity(goToListIntent);
    }
}
