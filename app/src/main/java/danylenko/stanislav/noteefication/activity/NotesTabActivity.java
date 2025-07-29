package danylenko.stanislav.noteefication.activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.view.WindowManager;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.fragment.FragmentsPagerAdapter;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.TAB_INDEX;

public class NotesTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_tab);

        ViewPager2 viewPager = findViewById(R.id.viewpager);
        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0: tab.setText(adapter.getPageTitle(0)); break;
                        case 1: tab.setText(adapter.getPageTitle(1)); break;
                    }
                }
        ).attach();

        Intent intent = getIntent();
        int tabIndex = intent.getIntExtra(TAB_INDEX, 0);

        viewPager.setCurrentItem(tabIndex);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            int bottom = Math.max(systemBars.bottom, imeInsets.bottom);
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, bottom);
            return insets;
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void cleanHistory(View view) {
        DBActionHandler.handleCleanHistoryAction();
    }

    public void cleanAllCurrent(View view) {
        DBActionHandler.handleMoveAllActiveToDoneAction(this);
    }
}
