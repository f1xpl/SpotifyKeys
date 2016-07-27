package spotifykeys.mtcn.com.spotifykeys;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeysService extends android.app.Service {
    @Override
    public void onCreate() {
        mApplicationPreferencesStorage = new ApplicationPreferencesStorage(this);
        mSpotifyProxy = new SpotifyProxy(mApplicationPreferencesStorage, this);

        mKeyEventsHandler.subscribe(this);
        mSpotifyProxy.subscribe();
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
                case MessageIds.KEY_CODE_OBTAIN:
                    mKeyCodeObtainerMessenger = msg.replyTo;
                    break;
            }
        }
    }

    private KeyEventsHandlerListener mKeyEventsHandlerListener = new KeyEventsHandlerListener() {
        @Override
        public void onKeyPressed(int keyCode) {
            if(mKeyCodeObtainerMessenger != null) {
                sendKeyCode(keyCode);
                mKeyCodeObtainerMessenger = null;
            } else if (keyCode == mApplicationPreferencesStorage.getWheelNextKeyCode()) {
                mSpotifyProxy.nextTrack();
            } else if (keyCode == mApplicationPreferencesStorage.getWheelPreviousKeyCode()) {
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

    private ApplicationPreferencesStorage mApplicationPreferencesStorage = null;
    private SpotifyProxy mSpotifyProxy = null;
    private final KeyEventsHandler mKeyEventsHandler = new KeyEventsHandler(mKeyEventsHandlerListener);
    private final Messenger mMessenger = new Messenger(new MessageHandler());
    private Messenger mKeyCodeObtainerMessenger = null;
}
