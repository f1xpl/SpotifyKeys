package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodes;
import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodesForPrevious;

public class KeyCodesForPreviousLearningActivity extends KeyCodesLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_codes_for_previous_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningPrevious);
        initKeyCodesListView(R.id.listViewKeyCodesForPrevious);
    }

    @Override
    KeyCodes createKeyCodesStorage() {
        return new KeyCodesForPrevious(getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE));
    }
}
