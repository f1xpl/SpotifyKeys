package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import spotifykeys.mtcn.com.spotifykeys.launch.KeyCodeForLaunchPreference;
import spotifykeys.mtcn.com.spotifykeys.next.KeyCodesForNextPreference;
import spotifykeys.mtcn.com.spotifykeys.players.PlayerProxiesController;
import spotifykeys.mtcn.com.spotifykeys.playpause.KeyCodesForPlayPausePreference;
import spotifykeys.mtcn.com.spotifykeys.previous.KeyCodesForPreviousPreference;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeysService extends android.app.Service {
    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE);
        mKeyCodesForNextPreference = new KeyCodesForNextPreference(sharedPreferences);
        mKeyCodesForPreviousPreference = new KeyCodesForPreviousPreference(sharedPreferences);
        mKeyCodesForPlayPausePreference = new KeyCodesForPlayPausePreference(sharedPreferences);
        mKeyCodeForLaunchPreference = new KeyCodeForLaunchPreference(sharedPreferences);

        mPlayerProxiesController = new PlayerProxiesController(this);
        mKeyEventsHandler = new KeyEventsHandler(this, mKeyEventsHandlerListener);

        mPlayerProxiesController.registerAll();
        mKeyEventsHandler.subscribe();
    }

    @Override public void onDestroy() {
        super.onDestroy();

        mKeyEventsHandler.unsubscribe();
        mPlayerProxiesController.unregisterAll();
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

    private class MessageHandler extends Handler {
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

    private final KeyEventsHandlerListener mKeyEventsHandlerListener = new KeyEventsHandlerListener() {
        @Override
        public void onKeyPressed(int keyCode) {
            String keyCodeString = Integer.toString(keyCode);

            if (mKeyCodeObtainerMessenger != null) {
                sendKeyCode(keyCode);
                mKeyCodeObtainerMessenger = null;
            } else if (mKeyCodeForLaunchPreference.get() == keyCode) {
                mPlayerProxiesController.launch();
            } else if(mKeyCodesForPlayPausePreference.get().contains(keyCodeString)) {
                mPlayerProxiesController.togglePlay();
            } else if (mKeyCodesForNextPreference.get().contains(keyCodeString)) {
                mPlayerProxiesController.switchToNextTrack();
            } else if (mKeyCodesForPreviousPreference.get().contains(keyCodeString)) {
                mPlayerProxiesController.switchToPreviousTrack();
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

    private KeyCodesForNextPreference mKeyCodesForNextPreference = null;
    private KeyCodesForPreviousPreference mKeyCodesForPreviousPreference = null;
    private KeyCodesForPlayPausePreference mKeyCodesForPlayPausePreference = null;
    private KeyCodeForLaunchPreference mKeyCodeForLaunchPreference = null;
    private PlayerProxiesController mPlayerProxiesController = null;
    private KeyEventsHandler mKeyEventsHandler = null;
    private final Messenger mMessenger = new Messenger(new MessageHandler());
    private Messenger mKeyCodeObtainerMessenger = null;
}
