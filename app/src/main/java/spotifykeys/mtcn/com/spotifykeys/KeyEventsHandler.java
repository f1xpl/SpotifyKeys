package spotifykeys.mtcn.com.spotifykeys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeyEventsHandler extends BroadcastReceiver {
    KeyEventsHandler(KeyEventsHandlerListener keyEventsHandlerListener) {
        mKeyEventsHandlerListener = keyEventsHandlerListener;
    }

    public void subscribe(Context context) {
        IntentFilter irkeyIntent = new IntentFilter();
        irkeyIntent.addAction(KEY_PRESSED_ACTION_NAME);
        context.registerReceiver(this, irkeyIntent);
    }

    @Override
    public void onReceive(Context context, Intent receivedIntent) {
        int keyCode = receivedIntent.getIntExtra(KEYCODE_PARAM_NAME, 0);
		mKeyEventsHandlerListener.onKeyPressed(keyCode);
    }

    private final KeyEventsHandlerListener mKeyEventsHandlerListener;
    private static final String KEYCODE_PARAM_NAME = "keyCode";
    private static final String KEY_PRESSED_ACTION_NAME = "com.microntek.irkeyDown";
}
