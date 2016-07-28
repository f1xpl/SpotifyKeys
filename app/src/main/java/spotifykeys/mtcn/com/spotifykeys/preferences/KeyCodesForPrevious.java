package spotifykeys.mtcn.com.spotifykeys.preferences;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public class KeyCodesForPrevious extends KeyCodes {
    public KeyCodesForPrevious(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    @Override
    protected String getKeyPrefix() {
        return KEY_PREFIX;
    }

    public static final String KEY_PREFIX = "KeyCodesForPrevious";
}
