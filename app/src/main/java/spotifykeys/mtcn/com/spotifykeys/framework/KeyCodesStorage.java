package spotifykeys.mtcn.com.spotifykeys.framework;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.CommitFailedException;
import spotifykeys.mtcn.com.spotifykeys.framework.preferences.KeyCodesPreference;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public abstract class KeyCodesStorage {
    protected KeyCodesStorage(Context context) {
        mKeyCodesPreference = createKeyCodes(context);
        mAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, new ArrayList<>(mKeyCodesPreference.get()));
    }

    protected abstract KeyCodesPreference createKeyCodes(Context context);
    ArrayAdapter<String> getAdapter() {
        return mAdapter;
    }

    boolean store(int keyCode) {
        String keyCodeString = Integer.toString(keyCode);

        if(!mKeyCodesPreference.get().contains(keyCodeString)) {
            mAdapter.add(keyCodeString);

            try {
                mKeyCodesPreference.insert(keyCode);
            } catch (CommitFailedException e) {
                e.printStackTrace();
                mAdapter.remove(keyCodeString);
                return false;
            }
        }

        return true;
    }

    boolean remove(int position) {
        String keyCodeString = mAdapter.getItem(position);;

        try {
            mKeyCodesPreference.remove(Integer.parseInt(keyCodeString));
            mAdapter.remove(keyCodeString);
        } catch (CommitFailedException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private final ArrayAdapter<String> mAdapter;
    private final KeyCodesPreference mKeyCodesPreference;
}
