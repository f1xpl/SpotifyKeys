package spotifykeys.mtcn.com.spotifykeys;

import android.content.Context;
import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodes;
import spotifykeys.mtcn.com.spotifykeys.preferences.KeyCodesForNext;

public class KeyCodesForNextLearningActivity extends KeyCodesLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_codes_for_next_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningNext);
        initKeyCodesListView(R.id.listViewKeyCodesForNext);
    }

    @Override
    KeyCodes createKeyCodesStorage() {
        return new KeyCodesForNext(getSharedPreferences(MainActivity.APP_NAME, Context.MODE_PRIVATE));
    }
}
