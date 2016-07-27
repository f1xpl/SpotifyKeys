package spotifykeys.mtcn.com.spotifykeys;

import android.os.CountDownTimer;

/**
 * Created by COMPUTER on 2016-07-26.
 */
public class KeyCodeLearner {
    KeyCodeLearner(ApplicationPreferencesStorage applicationPreferencesStorage, KeyCodeLearnerListener keyCodeLearnerListener) {
        mApplicationPreferencesStorage = applicationPreferencesStorage;
        mKeyCodeLearnerListener = keyCodeLearnerListener;

        mLearnKeyCodeForNextState = false;
        mLearnKeyCodeForPreviousState = false;
    }

    public boolean isLearning() {
        return mLearnKeyCodeForNextState || mLearnKeyCodeForPreviousState;
    }

    public void switchToLearnKeyCodeForNextState() {
        mLearnKeyCodeForNextState = true;
        mTimer.start();
    }

    public void switchToLearnKeyCodeForPreviousState() {
        mLearnKeyCodeForPreviousState = true;
        mTimer.start();
    }

    public void learnKeyCode(int keyCode) {
        try {
            if (mLearnKeyCodeForNextState) {
                mLearnKeyCodeForNextState = false;
                mApplicationPreferencesStorage.setWheelNextKeyCode(keyCode);
            } else if (mLearnKeyCodeForPreviousState) {
                mLearnKeyCodeForPreviousState = false;
                mApplicationPreferencesStorage.setWheelPreviousKeyCode(keyCode);
            }

            mKeyCodeLearnerListener.onSuccess();
        } catch (ApplicationPreferencesStorage.CommitFailedException e) {
            e.printStackTrace();
            mKeyCodeLearnerListener.onFailure();
        }

        mTimer.cancel();
    }

    private final CountDownTimer mTimer = new CountDownTimer(LEARN_TIMER_DURATION_MS, LEARN_TIMER_DURATION_MS) {
        @Override
        public void onTick(long l) {
        }

        @Override
        public void onFinish() {
            if (KeyCodeLearner.this.isLearning()) {
                mLearnKeyCodeForPreviousState = false;
                mLearnKeyCodeForNextState = false;
                mKeyCodeLearnerListener.onTimeout();
            }
        }
    };

    private final ApplicationPreferencesStorage mApplicationPreferencesStorage;
    private final KeyCodeLearnerListener mKeyCodeLearnerListener;
    private boolean mLearnKeyCodeForNextState;
    private boolean mLearnKeyCodeForPreviousState;

    private static final int LEARN_TIMER_DURATION_MS = 5000;
}
