package spotifykeys.mtcn.com.spotifykeys;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApplicationPreferencesStorage = new ApplicationPreferencesStorage(this);
        mKeyCodeLearner = new KeyCodeLearner(mApplicationPreferencesStorage, mKeyCodeLearnerListener);

        mLearningProgressDialog = new ProgressDialog(this);
        mLearningProgressDialog.setTitle("Waiting");
        mLearningProgressDialog.setMessage("Press the key you want to bind");
        mLearningProgressDialog.setCancelable(false);

        mLearnKeyCodeForPreviousButton = (Button)this.findViewById(R.id.buttonLearnPrev);
        mLearnKeyCodeForPreviousButton.setOnClickListener(new LearnKeyCodeForPreviousButtonClickListener());

        mLearnKeyCodeForNextButton = (Button)this.findViewById(R.id.buttonLearnNext);
        mLearnKeyCodeForNextButton.setOnClickListener(new LearnKeyCodeForNextButtonClickListener());

        mSwitchTrackWhenPausedSwitch = (Switch)this.findViewById(R.id.switchTrackWhenPausedSwitch);
        mSwitchTrackWhenPausedSwitch.setOnClickListener(new SwitchTrackWhenPausedSwitchClickListener());
        mSwitchTrackWhenPausedSwitch.setChecked(mApplicationPreferencesStorage.getSwitchTrackWhenPaused());

        startService();
    }

    private void startService() {
        Intent intent = new Intent(this, KeysService.class);
        this.startService(intent);

        mServiceConnection = new KeysListenerServiceConnection();
        this.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    void startKeyCodeLearning() {
        mLearnKeyCodeForPreviousButton.setClickable(false);
        mLearnKeyCodeForNextButton.setClickable(false);

        Message msg = new Message();
        msg.what = MessageIds.KEY_CODE_OBTAIN;
        msg.replyTo = mMessenger;

        try {
            mServiceMessenger.send(msg);
            mLearningProgressDialog.show();
        } catch (RemoteException e) {
            e.printStackTrace();
            mKeyCodeLearnerListener.onFailure();
            mLearningProgressDialog.dismiss();
        }
    }

    void finishKeyCodeLearning(String finishText) {
        mLearnKeyCodeForPreviousButton.setClickable(true);
        mLearnKeyCodeForNextButton.setClickable(true);

        mLearningProgressDialog.dismiss();
        Toast toast = Toast.makeText(MainActivity.this, finishText, Toast.LENGTH_LONG);
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

    class LearnKeyCodeForPreviousButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            if(!mKeyCodeLearner.isLearning()) {
                startKeyCodeLearning();
                mKeyCodeLearner.switchToLearnKeyCodeForPreviousState();
            }
        }
    }

    class LearnKeyCodeForNextButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            if(!mKeyCodeLearner.isLearning()) {
                startKeyCodeLearning();
                mKeyCodeLearner.switchToLearnKeyCodeForNextState();
            }
        }
    }

    class SwitchTrackWhenPausedSwitchClickListener implements Switch.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                boolean state = mApplicationPreferencesStorage.getSwitchTrackWhenPaused();
                mApplicationPreferencesStorage.setSwitchTrackWhenPaused(!state);
            } catch (ApplicationPreferencesStorage.CommitFailedException e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(MainActivity.this, "Failed to change option state", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageIds.KEY_CODE:
                    if(mKeyCodeLearner.isLearning()) {
                        mKeyCodeLearner.learnKeyCode(msg.arg1);
                    }
                    break;
            }
        }
    }

    private final KeyCodeLearnerListener mKeyCodeLearnerListener = new KeyCodeLearnerListener() {
        @Override
        public void onSuccess() {
            MainActivity.this.finishKeyCodeLearning("Key learning completed");
        }

        @Override
        public void onFailure() {
            MainActivity.this.finishKeyCodeLearning("Failed to learn the key");
        }

        @Override
        public void onTimeout() {
            MainActivity.this.finishKeyCodeLearning("Key learning timed out");
        }
    };

    private KeysListenerServiceConnection mServiceConnection = null;
    private ApplicationPreferencesStorage mApplicationPreferencesStorage = null;
    private KeyCodeLearner mKeyCodeLearner = null;

    private Button mLearnKeyCodeForPreviousButton = null;
    private Button mLearnKeyCodeForNextButton = null;
    private Switch mSwitchTrackWhenPausedSwitch = null;

    private Messenger mServiceMessenger = null;
    private Messenger mMessenger = new Messenger(new MessageHandler());
    private ProgressDialog mLearningProgressDialog = null;
}
