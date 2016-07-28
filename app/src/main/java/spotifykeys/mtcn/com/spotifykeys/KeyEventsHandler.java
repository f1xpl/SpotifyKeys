package spotifykeys.mtcn.com.spotifykeys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeyEventsHandler extends BroadcastReceiver {
    KeyEventsHandler(Context context, KeyEventsHandlerListener keyEventsHandlerListener) {
        mContext = context;
        mKeyEventsHandlerListener = keyEventsHandlerListener;
    }

    public void subscribe() {
        IntentFilter irkeyIntent = new IntentFilter();
        irkeyIntent.addAction(KEY_PRESSED_ACTION_NAME);
        mContext.registerReceiver(this, irkeyIntent);
    }

    public void unsubscribe() {
        mContext.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent receivedIntent) {
        int keyCode = receivedIntent.getIntExtra(KEYCODE_PARAM_NAME, 0);
		mKeyEventsHandlerListener.onKeyPressed(keyCode);
    }

    private final Context mContext;
    private final KeyEventsHandlerListener mKeyEventsHandlerListener;
    private static final String KEYCODE_PARAM_NAME = "keyCode";
    private static final String KEY_PRESSED_ACTION_NAME = "com.microntek.irkeyDown";
}
