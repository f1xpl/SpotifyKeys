package spotifykeys.mtcn.com.spotifykeys;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import spotifykeys.mtcn.com.spotifykeys.preferences.CommitFailedException;
import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodes;

/**
 * Created by COMPUTER on 2016-07-28.
 */
abstract class KeyCodesLearningActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mKeyCodes = createKeyCodesStorage();

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
        mKeyCodesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(mKeyCodes.get()));
        mKeyCodesListView.setAdapter(mKeyCodesAdapter);
    }

    abstract KeyCodes createKeyCodesStorage();

    private void startKeyCodeLearning() {
        mLearnKeyCodeButton.setClickable(false);

        try {
            Message msg = new Message();
            msg.what = MessageIds.OBTAIN_KEY_CODE;
            msg.replyTo = mMessenger;
            mServiceMessenger.send(msg);

            mLearningTimer.start();
            mLearningProgressDialog.show();
        } catch (RemoteException e) {
            e.printStackTrace();
            finishKeyCodeLearning("Failed to start learning procedure");
        }
    }

    private void learnKeyCode(int keyCode) {
        String keyCodeString = Integer.toString(keyCode);

        if(!mKeyCodes.get().contains(keyCodeString)) {
            mKeyCodesAdapter.add(keyCodeString);

            try {
                mKeyCodes.insert(keyCode);
                finishKeyCodeLearning("Key has been learnt");
            } catch (CommitFailedException e) {
                e.printStackTrace();
                mKeyCodesAdapter.remove(keyCodeString);
                finishKeyCodeLearning("Unable to save the key");
            }
        } else {
            finishKeyCodeLearning("Key has been already stored");
        }
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
                    KeyCodesLearningActivity.this.learnKeyCode(msg.arg1);
                    break;
            }
        }
    }

    private class LearnKeyCodeButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startKeyCodeLearning();
        }
    }

    private class LearntKeyCodeClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            String keyCode = mKeyCodesAdapter.getItem(position);

            try {
                mKeyCodes.remove(Integer.parseInt(keyCode));
                mKeyCodesAdapter.remove(keyCode);
            } catch (CommitFailedException e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(KeyCodesLearningActivity.this, "Unable to remove learnt key", Toast.LENGTH_LONG);
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
            KeyCodesLearningActivity.this.finishKeyCodeLearning("Key learning timed out");
        }
    };

    private KeyCodes mKeyCodes = null;

    private ProgressDialog mLearningProgressDialog = null;
    private Button mLearnKeyCodeButton = null;
    private ListView mKeyCodesListView = null;
    private ArrayAdapter<String> mKeyCodesAdapter = null;

    private KeysListenerServiceConnection mServiceConnection = null;
    private Messenger mServiceMessenger = null;
    private final Messenger mMessenger = new Messenger(new MessageHandler());

    private static final long LEARN_TIMER_DURATION_MS = 5000;
    private static final long LEARN_TIMER_STEP_MS = 1000;
};
