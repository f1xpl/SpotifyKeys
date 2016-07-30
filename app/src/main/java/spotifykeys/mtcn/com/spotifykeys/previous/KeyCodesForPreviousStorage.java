package spotifykeys.mtcn.com.spotifykeys.previous;

import android.content.Context;

import spotifykeys.mtcn.com.spotifykeys.MainActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesStorage;
import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodesPreference;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public class KeyCodesForPreviousStorage extends KeyCodesStorage {
    public KeyCodesForPreviousStorage(Context context) {
        super(context);
    }

    @Override
    protected KeyCodesPreference createKeyCodes(Context context) {
        return new KeyCodesForPreviousPreference(context.getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE));
    }
}
