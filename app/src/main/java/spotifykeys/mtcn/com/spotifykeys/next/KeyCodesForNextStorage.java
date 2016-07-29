package spotifykeys.mtcn.com.spotifykeys.next;

import android.content.Context;

import spotifykeys.mtcn.com.spotifykeys.MainActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesStorage;
import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodes;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public class KeyCodesForNextStorage extends KeyCodesStorage {
    public KeyCodesForNextStorage(Context context) {
        super(context);
    }

    @Override
    protected KeyCodes createKeyCodes(Context context) {
        return new KeyCodesForNextPreference(context.getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE));
    }
}
