package csc413_team19.storyfinder;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StoryListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StoryListFragment();
    }
}
