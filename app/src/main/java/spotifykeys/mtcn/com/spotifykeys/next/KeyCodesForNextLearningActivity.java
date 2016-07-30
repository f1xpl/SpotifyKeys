package spotifykeys.mtcn.com.spotifykeys.next;

import android.os.Bundle;

import spotifykeys.mtcn.com.spotifykeys.R;
import spotifykeys.mtcn.com.spotifykeys.framework.MultipleKeyCodesLearningActivity;
import spotifykeys.mtcn.com.spotifykeys.framework.KeyCodesStorage;

public class KeyCodesForNextLearningActivity extends MultipleKeyCodesLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_codes_for_next_learning);
        initLearnKeyCodeButton(R.id.buttonStartLearningNext);
        initKeyCodesListView(R.id.listViewKeyCodesForNext);
    }

    @Override
    protected KeyCodesStorage createKeyCodesStorage() {
        return new KeyCodesForNextStorage(this);
    }
}
