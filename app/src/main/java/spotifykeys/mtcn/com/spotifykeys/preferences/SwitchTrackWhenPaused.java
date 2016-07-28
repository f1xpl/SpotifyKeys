package spotifykeys.mtcn.com.spotifykeys.preferences;

import android.content.SharedPreferences;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class SwitchTrackWhenPaused extends Preference<Boolean> {
    public SwitchTrackWhenPaused(SharedPreferences sharedPreferences) {
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
