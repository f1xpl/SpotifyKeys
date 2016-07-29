package spotifykeys.mtcn.com.spotifykeys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        mLearnKeyCodeForPreviousButton.setOnClickListener(new LearnKeyCodeForPreviousButtonClickListener());

        mLearnKeyCodeForNextButton = (Button)this.findViewById(R.id.buttonLearnNext);
        mLearnKeyCodeForNextButton.setOnClickListener(new LearnKeyCodeForNextButtonClickListener());

        mLearnKeyCodeForNextButton = (Button)this.findViewById(R.id.buttonLearnPlayPause);
        mLearnKeyCodeForNextButton.setOnClickListener(new LearnKeyCodeForPlayPauseButtonClickListener());

        this.startService(new Intent(this, KeysService.class));
    }

    private class LearnKeyCodeForPreviousButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, KeyCodesForPreviousLearningActivity.class));
        }
    }

    private class LearnKeyCodeForNextButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, KeyCodesForNextLearningActivity.class));
        }
    }

    private class LearnKeyCodeForPlayPauseButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, KeyCodesForPlayPauseLearningActivity.class));
        }
    }

    private Button mLearnKeyCodeForPreviousButton = null;
    private Button mLearnKeyCodeForNextButton = null;
}
