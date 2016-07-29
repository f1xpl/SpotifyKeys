package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import spotifykeys.mtcn.com.spotifykeys.next.KeyCodesForNextPreference;
import spotifykeys.mtcn.com.spotifykeys.previous.KeyCodesForPreviousPreference;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeysService extends android.app.Service {
    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE);
        mSwitchTrackWhenPausedPreference = new SwitchTrackWhenPausedPreference(sharedPreferences);
        mKeyCodesForNextPreference = new KeyCodesForNextPreference(sharedPreferences);
        mKeyCodesForPreviousPreference = new KeyCodesForPreviousPreference(sharedPreferences);
        mSpotifyProxy = new SpotifyProxy(this, mSwitchTrackWhenPausedPreference);
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
                case MessageIds.OBTAIN_KEY_CODE_ABORTED:
                    mKeyCodeObtainerMessenger = null;
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
            } else if (mKeyCodesForNextPreference.get().contains(keyCodeString)) {
                mSpotifyProxy.nextTrack();
            } else if (mKeyCodesForPreviousPreference.get().contains(keyCodeString)) {
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

    private SwitchTrackWhenPausedPreference mSwitchTrackWhenPausedPreference = null;
    private KeyCodesForNextPreference mKeyCodesForNextPreference = null;
    private KeyCodesForPreviousPreference mKeyCodesForPreviousPreference = null;
    private SpotifyProxy mSpotifyProxy = null;
    private KeyEventsHandler mKeyEventsHandler = null;
    private final Messenger mMessenger = new Messenger(new MessageHandler());
    private Messenger mKeyCodeObtainerMessenger = null;
}
