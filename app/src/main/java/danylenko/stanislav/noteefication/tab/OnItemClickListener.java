package danylenko.stanislav.noteefication.tab;

import android.widget.TextView;

import danylenko.stanislav.noteefication.db.Note;

public interface OnItemClickListener {
    void onMenuClick(Note item);
    void onEmojiClick(Note item, TextView textView);
}
