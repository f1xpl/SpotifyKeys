package spotifykeys.mtcn.com.spotifykeys.launch;

import android.content.SharedPreferences;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodePreference;

/**
 * Created by COMPUTER on 2016-07-30.
 */
public class KeyCodeForLaunchPreference extends KeyCodePreference {
    public KeyCodeForLaunchPreference(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected String getKeyPrefix() {
        return KEYCODE_PREFIX;
    }

    private static final String KEYCODE_PREFIX = "KeyCodeForLaunch";
}
