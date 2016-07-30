package spotifykeys.mtcn.com.spotifykeys.players;

import android.content.Intent;

import spotifykeys.mtcn.com.spotifykeys.framework.PlayerProxy;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public class SpotifyProxy extends PlayerProxy {
    @Override
    public String getPlaybackStateActionName() {
        return PLAYBACK_STATE_ACTION_NAME;
    }

    @Override
    public Intent getNextTrackIntent() {
        return new Intent(NEXT_TRACK_ACTION_NAME);
    }

    @Override
    public Intent getPreviousTrackIntent() {
        return new Intent(PREVIOUS_TRACK_ACTION_NAME);
    }

    @Override
    public Intent getPlayIntent() {
        Intent intent = new Intent(PLAY_ACTION_NAME);
        intent.putExtra(PAUSED_STATE_PARAM_NAME, true);
        return intent;
    }

    @Override
    public Intent getPauseIntent() {
        Intent intent = new Intent(PLAY_ACTION_NAME);
        intent.putExtra(PAUSED_STATE_PARAM_NAME, false);
        return intent;
    }

    @Override
    public String getPackageName() {
        return PACKAGE_NAME;
    }

    @Override
    protected boolean readPlaybackStateFromIntent(Intent intent) {
        return intent.getBooleanExtra(PLAYBACK_STATE_PARAM_NAME, false);
    }

    private static final String PACKAGE_NAME = "com.spotify.music";
    private static final String PLAYBACK_STATE_PARAM_NAME = "playing";
    private static final String PAUSED_STATE_PARAM_NAME = "paused";
    private static final String PLAYBACK_STATE_ACTION_NAME = "com.spotify.music.playbackstatechanged";
    private static final String PREVIOUS_TRACK_ACTION_NAME = "com.spotify.mobile.android.ui.widget.PREVIOUS";
    private static final String NEXT_TRACK_ACTION_NAME = "com.spotify.mobile.android.ui.widget.NEXT";
    private static final String PLAY_ACTION_NAME = "com.spotify.mobile.android.ui.widget.PLAY";
}
