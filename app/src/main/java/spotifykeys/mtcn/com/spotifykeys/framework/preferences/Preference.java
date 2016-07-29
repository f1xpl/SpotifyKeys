package spotifykeys.mtcn.com.spotifykeys.framework.preferences;

import android.content.SharedPreferences;

import spotifykeys.mtcn.com.spotifykeys.framework.preferences.CommitFailedException;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public abstract class Preference<T> {
    public Preference(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mEditor = sharedPreferences.edit();
        mValue = read();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mChangeListener);
    }

    public T get() {
        return mValue;
    }

    public void set(T value) throws CommitFailedException {
        write(value);
        commit();
    }

    protected abstract T read();
    protected abstract void write(T value);

    private void commit() throws CommitFailedException {
        if(!mEditor.commit()) {
            throw new CommitFailedException();
        }
    }

    protected abstract String getKeyPrefix();

    private SharedPreferences.OnSharedPreferenceChangeListener mChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefix) {
            if(prefix == getKeyPrefix()) {
                mValue = read();
            }
        }
    };

    protected final SharedPreferences mSharedPreferences;
    protected final SharedPreferences.Editor mEditor;
    private T mValue;
}
