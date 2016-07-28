package spotifykeys.mtcn.com.spotifykeys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import spotifykeys.mtcn.com.spotifykeys.preferences.SwitchTrackWhenPaused;

/**
 * Created by COMPUTER on 2016-07-27.
 */
public class SpotifyProxy extends BroadcastReceiver {
    SpotifyProxy(Context context, SwitchTrackWhenPaused switchTrackWhenPaused) {
        mContext = context;
        mSwitchTrackWhenPaused = switchTrackWhenPaused;
        mIsPlaybackActive = false;
    }

    @Override
    public void onReceive(Context context, Intent receivedIntent) {
        mIsPlaybackActive = receivedIntent.getBooleanExtra(PLAYBACK_STATE_PARAM_NAME, false);
    }

    public void subscribe() {
        IntentFilter playbackStateIntent = new IntentFilter();
        playbackStateIntent.addAction(PLAYBACK_STATE_ACTION_NAME);
        mContext.registerReceiver(this, playbackStateIntent);
    }

    public void unsubscribe() {
        mContext.unregisterReceiver(this);
    }

    public void nextTrack() {
        if(mIsPlaybackActive || mSwitchTrackWhenPaused.get()) {
            this.sendCommand(NEXT_TRACK_COMMAND);
        }
    }

    public void previousTrack() {
        if(mIsPlaybackActive || mSwitchTrackWhenPaused.get()) {
            this.sendCommand(PREVIOUS_TRACK_COMMAND);
        }
    }

    private void sendCommand(String action) {
        Intent spotifyCommandIntent = new Intent();
        spotifyCommandIntent.setAction(action);
        mContext.sendBroadcast(spotifyCommandIntent);
    }

    private final Context mContext;
    private final SwitchTrackWhenPaused mSwitchTrackWhenPaused;
    private boolean mIsPlaybackActive;

    private static final String PLAYBACK_STATE_PARAM_NAME = "playing";
    private static final String PLAYBACK_STATE_ACTION_NAME = "com.spotify.music.playbackstatechanged";
    private static final String PREVIOUS_TRACK_COMMAND = "com.spotify.mobile.android.ui.widget.PREVIOUS";
    private static final String NEXT_TRACK_COMMAND = "com.spotify.mobile.android.ui.widget.NEXT";
}
