package spotifykeys.mtcn.com.spotifykeys.framework;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import spotifykeys.mtcn.com.spotifykeys.KeysService;
import spotifykeys.mtcn.com.spotifykeys.MessageIds;

/**
 * Created by COMPUTER on 2016-07-28.
 */
public abstract class KeyCodesLearningActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeyCodesStorage = createKeyCodesStorage();

        mLearningProgressDialog = new ProgressDialog(this);
        mLearningProgressDialog.setTitle("Waiting for key press");
        mLearningProgressDialog.setCancelable(false);

        mServiceConnection = new KeysListenerServiceConnection();
        this.bindService(new Intent(this, KeysService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mServiceConnection != null && mServiceMessenger != null) {
            this.unbindService(mServiceConnection);
        }
    }

    protected void initLearnKeyCodeButton(int id) {
        mLearnKeyCodeButton = (Button)this.findViewById(id);
        mLearnKeyCodeButton.setOnClickListener(new LearnKeyCodeButtonClickListener());
    }

    protected void initKeyCodesListView(int id) {
        mKeyCodesListView = (ListView)this.findViewById(id);
        mKeyCodesListView.setOnItemLongClickListener(new LearntKeyCodeClickListener());
        mKeyCodesListView.setAdapter(mKeyCodesStorage.getAdapter());
    }

    protected abstract KeyCodesStorage createKeyCodesStorage();

    private void sendMessage(int messageId) throws RemoteException {
        Message msg = new Message();
        msg.what = messageId;
        msg.replyTo = mMessenger;
        mServiceMessenger.send(msg);
    }

    private void finishKeyCodeLearning(String finishText) {
        mLearningTimer.cancel();
        mLearnKeyCodeButton.setClickable(true);
        mLearningProgressDialog.dismiss();

        Toast toast = Toast.makeText(this, finishText, Toast.LENGTH_LONG);
        toast.show();
    }

    private class KeysListenerServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }

    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageIds.KEY_CODE:
                    String finishText = mKeyCodesStorage.store(msg.arg1) ? "Key code has been learnt" : "Key code learning failed";
                    finishKeyCodeLearning(finishText);
                    break;
            }
        }
    }

    private class LearnKeyCodeButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            mLearnKeyCodeButton.setClickable(false);

            try {
                sendMessage(MessageIds.OBTAIN_KEY_CODE);
                mLearningTimer.start();
                mLearningProgressDialog.show();
            } catch (RemoteException e) {
                e.printStackTrace();
                finishKeyCodeLearning("Failed to start learning procedure");
            }
        }
    }

    private class LearntKeyCodeClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            if(!mKeyCodesStorage.remove(position)) {
                Toast toast = Toast.makeText(KeyCodesLearningActivity.this, "Unable to remove the key", Toast.LENGTH_LONG);
                toast.show();
            }

            return true;
        }
    }

    private final CountDownTimer mLearningTimer = new CountDownTimer(LEARN_TIMER_DURATION_MS, LEARN_TIMER_STEP_MS) {
        @Override
        public void onTick(long l) {
            mLearningProgressDialog.setMessage("Press the key you want to bind in " + Long.toString(l / 1000) + "s");
        }

        @Override
        public void onFinish() {
            try {
                sendMessage(MessageIds.OBTAIN_KEY_CODE_ABORTED);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            KeyCodesLearningActivity.this.finishKeyCodeLearning("Key learning timed out");
        }
    };

    private KeyCodesStorage mKeyCodesStorage = null;

    private ProgressDialog mLearningProgressDialog = null;
    private Button mLearnKeyCodeButton = null;
    private ListView mKeyCodesListView = null;

    private KeysListenerServiceConnection mServiceConnection = null;
    private Messenger mServiceMessenger = null;
    private final Messenger mMessenger = new Messenger(new MessageHandler());

    private static final long LEARN_TIMER_DURATION_MS = 5000;
    private static final long LEARN_TIMER_STEP_MS = 1000;
}
