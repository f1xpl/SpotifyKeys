package spotifykeys.mtcn.com.spotifykeys.framework;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.CommitFailedException;
import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodePreference;

/**
 * Created by COMPUTER on 2016-07-30.
 */
public abstract class SingleKeyCodeLearningActivity extends KeyCodeLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyCodePreference = createKeyCodePreference();
    }

    protected abstract KeyCodePreference createKeyCodePreference();

    protected void initKeyCodeTextView(int id) {
        mKeyCodeTextView = (TextView)findViewById(id);
        setKeyCodeText(mKeyCodePreference.get());
        mKeyCodeTextView.setOnLongClickListener(new LearntKeyCodeClickListener());
    }

    @Override
    protected boolean storeKeyCode(int keyCode) {
        try {
            mKeyCodePreference.set(keyCode);
            setKeyCodeText(keyCode);
        } catch (CommitFailedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void setKeyCodeText(int keyCode) {
        mKeyCodeTextView.setText("Key code: < " + Integer.toString(keyCode) + " >");
    }

    private class LearntKeyCodeClickListener implements TextView.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            try {
                mKeyCodePreference.reset();
                mKeyCodeTextView.setText("Key code: < NONE >");
            } catch (CommitFailedException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    TextView mKeyCodeTextView = null;
    KeyCodePreference mKeyCodePreference = null;
}
