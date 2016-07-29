package spotifykeys.mtcn.com.spotifykeys.playpause;

import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.R;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesStorage;

public class KeyCodesForPlayPauseLearningActivity extends KeyCodesLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_codes_for_play_pause_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningPlayPause);
        initKeyCodesListView(R.id.listViewKeyCodesForPlayPause);
    }

    @Override
    protected KeyCodesStorage createKeyCodesStorage() {
        return new KeyCodesForPlayPauseStorage(this);
    }
}
