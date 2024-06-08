package danylenko.stanislav.noteefication.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.util.PermissionUtils;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;


public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFullScreenMode();

        editText = findViewById(R.id.editText);
        requestNotificationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFullScreenMode();
    }

    private void setFullScreenMode() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public void process(View view) {
        String value = editText.getText().toString();
        if (!value.isEmpty()) {
            DBActionHandler.handleAddAction(this, value, getIntent());
            editText.setText("");
        }
    }

    public void goToList(View view) {
        Intent goToListIntent = new Intent(this, NotesTabActivity.class);
        startActivity(goToListIntent);
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (!shouldShowRequestPermissionRationale(PermissionUtils.PERMISSION_REQUEST_CODE_STR)){
                PermissionUtils.getNotificationPermission(this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.processAnswer(this, requestCode, grantResults);
    }
}
