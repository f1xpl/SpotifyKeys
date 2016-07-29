package spotifykeys.mtcn.com.spotifykeys;

import android.content.SharedPreferences;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.Preference;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class SwitchTrackWhenPausedPreference extends Preference<Boolean> {
    public SwitchTrackWhenPausedPreference(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected Boolean read() {
        return mSharedPreferences.getBoolean(KEY_PREFIX, DEFAULT_VALUE);
    }

    @Override
    protected void write(Boolean value) {
        mEditor.putBoolean(KEY_PREFIX, value);
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    private static final boolean DEFAULT_VALUE = false;
    public static final String KEY_PREFIX = "SwitchTrackWhenPaused";
}
