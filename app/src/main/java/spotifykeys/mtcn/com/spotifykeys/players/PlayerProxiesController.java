package spotifykeys.mtcn.com.spotifykeys.players;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

import spotifykeys.mtcn.com.spotifykeys.framework.PlayerProxy;

/**
 * Created by COMPUTER on 2016-07-29.
 */
public class PlayerProxiesController {
    public PlayerProxiesController(Context context) {
        mContext = context;

        mPlayerProxies = new ArrayList<>();
        mPlayerProxies.add(new SpotifyProxy());
    }

    public void registerAll() {
        for(PlayerProxy playerProxy : mPlayerProxies) {
            register(playerProxy);
        }
    }

    public void unregisterAll() {
        for(PlayerProxy playerProxy : mPlayerProxies) {
            unregister(playerProxy);
        }
    }

    public void switchToNextTrack() {
        for(PlayerProxy playerProxy : mPlayerProxies) {
            if(playerProxy.isPlaying()) {
                mContext.sendBroadcast(playerProxy.getNextTrackIntent());
            }
        }
    }

    public void switchToPreviousTrack() {
        for(PlayerProxy playerProxy : mPlayerProxies) {
            if(playerProxy.isPlaying()) {
                mContext.sendBroadcast(playerProxy.getPreviousTrackIntent());
            }
        }
    }

    public void togglePlay() {
        PlayerProxy playerProxy = mPlayerProxies.get(0); // TODO: Support more players
        Intent intent = playerProxy.isPlaying() ? playerProxy.getPauseIntent() : playerProxy.getPlayIntent();
        mContext.sendBroadcast(intent);
    }

    private void register(PlayerProxy playerProxy) {
        IntentFilter playbackStateIntent = new IntentFilter();
        playbackStateIntent.addAction(playerProxy.getPlaybackStateActionName());
        mContext.registerReceiver(playerProxy, playbackStateIntent);
    }

    private void unregister(PlayerProxy playerProxy) {
        mContext.unregisterReceiver(playerProxy);
    }

    private final Context mContext;
    private final ArrayList<PlayerProxy> mPlayerProxies;
}
