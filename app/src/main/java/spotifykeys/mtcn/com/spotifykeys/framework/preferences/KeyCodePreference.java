package spotifykeys.mtcn.com.spotifykeys.framework.preferences;

import android.content.SharedPreferences;

/**
 * Created by COMPUTER on 2016-07-30.
 */
public abstract class KeyCodePreference extends Preference<Integer> {
    public KeyCodePreference(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected Integer read() {
        return mSharedPreferences.getInt(getKeyPrefix(), DEFAULT_VALUE);
    }

    @Override
    protected void write(Integer value) {
        mEditor.putInt(getKeyPrefix(), value);
    }

    @Override
    protected Integer getDefaultValue() {
        return DEFAULT_VALUE;
    }

    private static final int DEFAULT_VALUE = -1;
}
