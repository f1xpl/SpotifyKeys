package spotifykeys.mtcn.com.spotifykeys;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class ApplicationPreferencesStorage {
    public class CommitFailedException extends Throwable {}

    ApplicationPreferencesStorage(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENCES_PREFIX, Activity.MODE_PRIVATE);
        mPreferencesEditor = mPreferences.edit();
    }

    public boolean getSwitchTrackWhenPaused() {
        return mPreferences.getBoolean(SWITCH_TRACK_WHEN_PAUSED_PREFIX, DEFAULT_SWITCH_TRACK_WHEN_PAUSED);
    }

    public int getWheelPreviousKeyCode() {
        return mPreferences.getInt(WHEEL_PREVIOUS_KEYCODE_PREFIX, DEFAULT_WHEEL_PREVIOUS_KEYCODE);
    }

    public int getWheelNextKeyCode() {
        return mPreferences.getInt(WHEEL_NEXT_KEYCODE_PREFIX, DEFAULT_WHEEL_NEXT_KEYCODE);
    }

    public void setWheelPreviousKeyCode(int value) throws CommitFailedException {
        this.setKeyCode(WHEEL_PREVIOUS_KEYCODE_PREFIX, value);
    }

    public void setWheelNextKeyCode(int value) throws CommitFailedException {
        this.setKeyCode(WHEEL_NEXT_KEYCODE_PREFIX, value);
    }

    public void setSwitchTrackWhenPaused(boolean value) throws CommitFailedException {
        mPreferencesEditor.putBoolean(SWITCH_TRACK_WHEN_PAUSED_PREFIX, value);
        if(!mPreferencesEditor.commit()) {
            throw new CommitFailedException();
        }
    }

    private void setKeyCode(String keyCodePrefix, int value) throws CommitFailedException {
        mPreferencesEditor.putInt(keyCodePrefix, value);

        if(!mPreferencesEditor.commit()) {
            throw new CommitFailedException();
        }
    }

    private final SharedPreferences mPreferences;
    private final SharedPreferences.Editor mPreferencesEditor;

    private static final String SWITCH_TRACK_WHEN_PAUSED_PREFIX = "SwitchTrackWhenPaused";
    private static final boolean DEFAULT_SWITCH_TRACK_WHEN_PAUSED = false;

    private static final String WHEEL_PREVIOUS_KEYCODE_PREFIX = "WheelPreviousKeyCode";
    private static final int DEFAULT_WHEEL_PREVIOUS_KEYCODE = 276; // Key code for BMW E39

    private static final String WHEEL_NEXT_KEYCODE_PREFIX = "WheelNextKeyCode";
    private static final int DEFAULT_WHEEL_NEXT_KEYCODE = 278; // Key code for BMW E39

    private static final String PREFERENCES_PREFIX = "SpotifyKeysAppPreferences";
}
