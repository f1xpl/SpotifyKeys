package spotifykeys.mtcn.com.spotifykeys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import spotifykeys.mtcn.com.spotifykeys.launch.KeyCodeForLaunchLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.next.KeyCodesForNextLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.playpause.KeyCodesForPlayPauseLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.previous.KeyCodesForPreviousLearningActivity;

public class MainActivity extends AppCompatActivity {
    public static final String APP_NAME = "SpotifyKeys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLearnKeyCodeForPreviousButton = (Button)this.findViewById(R.id.buttonLearnPrevious);
        mLearnKeyCodeForPreviousButton.setOnClickListener(new StartLearningActivityButtonClickListener(KeyCodesForPreviousLearningActivity.class));

        mLearnKeyCodeForNextButton = (Button)this.findViewById(R.id.buttonLearnNext);
        mLearnKeyCodeForNextButton.setOnClickListener(new StartLearningActivityButtonClickListener(KeyCodesForNextLearningActivity.class));

        mLearnKeyCodeForPlayPause = (Button)this.findViewById(R.id.buttonLearnPlayPause);
        mLearnKeyCodeForPlayPause.setOnClickListener(new StartLearningActivityButtonClickListener(KeyCodesForPlayPauseLearningActivity.class));

        mLearnKeyCodeForLaunch = (Button)this.findViewById(R.id.buttonLearnLaunch);
        mLearnKeyCodeForLaunch.setOnClickListener(new StartLearningActivityButtonClickListener(KeyCodeForLaunchLearningActivity.class));

        this.startService(new Intent(this, KeysService.class));
    }

    private class StartLearningActivityButtonClickListener implements Button.OnClickListener {
        StartLearningActivityButtonClickListener(Class<? extends Activity> activity) {
            mActivity = activity;
        }

        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, mActivity));
        }

        private final Class<? extends Activity> mActivity;
    }

    private Button mLearnKeyCodeForPreviousButton = null;
    private Button mLearnKeyCodeForNextButton = null;
    private Button mLearnKeyCodeForPlayPause = null;
    private Button mLearnKeyCodeForLaunch = null;
}
