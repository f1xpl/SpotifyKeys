package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodesForNext;
import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodesForPrevious;
import spotifykeys.mtcn.com.spotifykeys.preferences.SwitchTrackWhenPaused;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeysService extends android.app.Service {
    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE);
        mSwitchTrackWhenPaused = new SwitchTrackWhenPaused(sharedPreferences);
        mKeyCodesForNext = new KeyCodesForNext(sharedPreferences);
        mKeyCodesForPrevious = new KeyCodesForPrevious(sharedPreferences);
        mSpotifyProxy = new SpotifyProxy(this, mSwitchTrackWhenPaused);
        mKeyEventsHandler = new KeyEventsHandler(this, mKeyEventsHandlerListener);

        mKeyEventsHandler.subscribe();
        mSpotifyProxy.subscribe();
    }

    @Override public void onDestroy() {
        super.onDestroy();

        mKeyEventsHandler.unsubscribe();
        mSpotifyProxy.unsubscribe();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        
        return START_STICKY;
    }    

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MessageIds.OBTAIN_KEY_CODE:
                    mKeyCodeObtainerMessenger = msg.replyTo;
                    break;
            }
        }
    }

    private KeyEventsHandlerListener mKeyEventsHandlerListener = new KeyEventsHandlerListener() {
        @Override
        public void onKeyPressed(int keyCode) {
            String keyCodeString = Integer.toString(keyCode);

            if(mKeyCodeObtainerMessenger != null) {
                sendKeyCode(keyCode);
                mKeyCodeObtainerMessenger = null;
            } else if (mKeyCodesForNext.get().contains(keyCodeString)) {
                mSpotifyProxy.nextTrack();
            } else if (mKeyCodesForPrevious.get().contains(keyCodeString)) {
                mSpotifyProxy.previousTrack();
            }
        }

        private void sendKeyCode(int keyCode) {
            Message msg = new Message();
            msg.what = MessageIds.KEY_CODE;
            msg.arg1 = keyCode;

            try {
                mKeyCodeObtainerMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private SwitchTrackWhenPaused mSwitchTrackWhenPaused = null;
    private KeyCodesForNext mKeyCodesForNext = null;
    private KeyCodesForPrevious mKeyCodesForPrevious = null;
    private SpotifyProxy mSpotifyProxy = null;
    private KeyEventsHandler mKeyEventsHandler = null;
    private final Messenger mMessenger = new Messenger(new MessageHandler());
    private Messenger mKeyCodeObtainerMessenger = null;
}
