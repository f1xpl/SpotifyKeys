package spotifykeys.mtcn.com.spotifykeys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by COMPUTER on 2016-07-27.
 */
public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, KeysService.class);
        context.startService(startServiceIntent);
    }
}
