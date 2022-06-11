package danylenko.stanislav.noteefication.util.modal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import danylenko.stanislav.noteefication.R;
import danylenko.stanislav.noteefication.db.Note;
import danylenko.stanislav.noteefication.util.db.DBActionHandler;

import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.CANCEL;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.EDIT_NOTE;
import static danylenko.stanislav.noteefication.constants.NoteeficationApplicationConstants.OK;

public final class ModalUtils {

    private ModalUtils() {
    }

    public static void showDialog(final Context context, final Note note, final Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.EditDialogTheme);
        builder.setTitle(EDIT_NOTE);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View editTextView = layoutInflater.inflate(R.layout.dialog_edit, null);
        builder.setView(editTextView);
        final TextView editText = editTextView.findViewById(R.id.value);
        editText.setText(note.text);


        builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedText = editText.getText().toString();
                if (!"".equals(editedText)) {
                    DBActionHandler.handleEditAction(context, note.id, editedText, intent);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
