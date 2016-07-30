package spotifykeys.mtcn.com.spotifykeys.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public abstract class PlayerProxy extends BroadcastReceiver {
    public PlayerProxy() {
        mIsPlaybackActive = false;
    }

    public abstract String getPlaybackStateActionName();
    public abstract String getPackageName();

    public abstract Intent getNextTrackIntent();
    public abstract Intent getPreviousTrackIntent();
    public abstract Intent getPlayIntent();
    public abstract Intent getPauseIntent();
    protected abstract boolean readPlaybackStateFromIntent(Intent intent);

    public boolean isPlaying() {
        return mIsPlaybackActive;
    }

    @Override
    public void onReceive(Context context, Intent receivedIntent) {
        mIsPlaybackActive = readPlaybackStateFromIntent(receivedIntent);
    }

    private boolean mIsPlaybackActive;
}
