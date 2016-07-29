package spotifykeys.mtcn.com.spotifykeys.previous;

import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.R;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesStorage;

public class KeyCodesForPreviousLearningActivity extends KeyCodesLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_codes_for_previous_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningPrevious);
        initKeyCodesListView(R.id.listViewKeyCodesForPrevious);
    }

    @Override
    protected KeyCodesStorage createKeyCodesStorage() {
        return new KeyCodesForPreviousStorage(this);
    }
}
