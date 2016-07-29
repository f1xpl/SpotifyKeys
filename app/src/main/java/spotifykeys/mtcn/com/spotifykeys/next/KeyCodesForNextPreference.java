package spotifykeys.mtcn.com.spotifykeys.next;

import android.content.SharedPreferences;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodes;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class KeyCodesForNextPreference extends KeyCodes {
    public KeyCodesForNextPreference(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    public static final String KEY_PREFIX = "KeyCodesForNext";
}
