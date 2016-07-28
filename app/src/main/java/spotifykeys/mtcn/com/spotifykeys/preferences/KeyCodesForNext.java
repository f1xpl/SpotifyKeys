package spotifykeys.mtcn.com.spotifykeys.preferences;

import android.content.SharedPreferences;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class KeyCodesForNext extends KeyCodes {
    public KeyCodesForNext(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    public static final String KEY_PREFIX = "KeyCodesForNext";
}
