package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import spotifykeys.mtcn.com.spotifykeys.preferences.CommitFailedException;
import spotifykeys.mtcn.com.spotifykeys.preferences.SwitchTrackWhenPaused;

public class MainActivity extends AppCompatActivity {
    public static final String APP_NAME = "SpotifyKeysPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchTrackWhenPaused = new SwitchTrackWhenPaused(getSharedPreferences(APP_NAME, Context.MODE_PRIVATE));

        mLearnKeyCodeForPreviousButton = (Button)this.findViewById(R.id.buttonLearnPrevious);
        mLearnKeyCodeForPreviousButton.setOnClickListener(new LearnKeyCodeForPreviousButtonClickListener());

        mLearnKeyCodeForNextButton = (Button)this.findViewById(R.id.buttonLearnNext);
        mLearnKeyCodeForNextButton.setOnClickListener(new LearnKeyCodeForNextButtonClickListener());

        mSwitchTrackWhenPausedSwitch = (Switch)this.findViewById(R.id.switchSwitchTrackWhenPaused);
        mSwitchTrackWhenPausedSwitch.setOnClickListener(new SwitchTrackWhenPausedSwitchClickListener());
        mSwitchTrackWhenPausedSwitch.setChecked(mSwitchTrackWhenPaused.get());

        this.startService(new Intent(this, KeysService.class));
    }

    class LearnKeyCodeForPreviousButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, KeyCodesForPreviousLearningActivity.class));
        }
    }

    class LearnKeyCodeForNextButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, KeyCodesForNextLearningActivity.class));
        }
    }

    class SwitchTrackWhenPausedSwitchClickListener implements Switch.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                boolean state = mSwitchTrackWhenPaused.get();
                mSwitchTrackWhenPaused.set(!state);
            } catch (CommitFailedException e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(MainActivity.this, "Failed to change option state", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    private SwitchTrackWhenPaused mSwitchTrackWhenPaused = null;
    private Button mLearnKeyCodeForPreviousButton = null;
    private Button mLearnKeyCodeForNextButton = null;
    private Switch mSwitchTrackWhenPausedSwitch = null;
}
