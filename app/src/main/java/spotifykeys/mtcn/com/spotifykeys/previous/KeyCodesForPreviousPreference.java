package spotifykeys.mtcn.com.spotifykeys.previous;

import android.content.SharedPreferences;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodes;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class KeyCodesForPreviousPreference extends KeyCodes {
    public KeyCodesForPreviousPreference(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    private static final String KEY_PREFIX = "KeyCodesForPrevious";
}
