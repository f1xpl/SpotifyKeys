package spotifykeys.mtcn.com.spotifykeys.launch;

import android.content.Context;
import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.MainActivity;
import spotifykeys.mtcn.com.spotifykeys.R;
import spotifykeys.mtcn.com.spotifykeys.framework.SingleKeyCodeLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodePreference;

/**
 * Created by COMPUTER on 2016-07-30.
 */
public class KeyCodeForLaunchLearningActivity extends SingleKeyCodeLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_code_for_launch_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningLaunch);
        initKeyCodeTextView(R.id.textViewKeyCodeForLaunch);
    }

    @Override
    protected KeyCodePreference createKeyCodePreference() {
        return new KeyCodeForLaunchPreference(getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE));
    }
}
