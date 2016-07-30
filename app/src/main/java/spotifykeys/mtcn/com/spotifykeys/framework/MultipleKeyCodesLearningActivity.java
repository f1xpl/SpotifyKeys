package spotifykeys.mtcn.com.spotifykeys.framework;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by COMPUTER on 2016-07-30.
 */
public abstract class MultipleKeyCodesLearningActivity extends KeyCodeLearningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyCodesStorage = createKeyCodesStorage();
    }

    protected void initKeyCodesListView(int id) {
        mKeyCodesListView = (ListView)this.findViewById(id);
        mKeyCodesListView.setOnItemLongClickListener(new LearntKeyCodeClickListener());
        mKeyCodesListView.setAdapter(mKeyCodesStorage.getAdapter());
    }

    protected abstract KeyCodesStorage createKeyCodesStorage();

    @Override
    protected boolean storeKeyCode(int keyCode) {
        return mKeyCodesStorage.store(keyCode);
    }

    private class LearntKeyCodeClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            if(!mKeyCodesStorage.remove(position)) {
                Toast toast = Toast.makeText(MultipleKeyCodesLearningActivity.this, "Unable to remove the key", Toast.LENGTH_LONG);
                toast.show();
            }

            return true;
        }
    }

    private KeyCodesStorage mKeyCodesStorage = null;
    private ListView mKeyCodesListView = null;
}
