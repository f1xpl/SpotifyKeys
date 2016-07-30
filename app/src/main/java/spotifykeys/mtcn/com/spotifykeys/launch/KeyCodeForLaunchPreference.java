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
        return KEY_PREFIX;
    }

    private static final String KEY_PREFIX = "KeyCodeForLaunch";
}
