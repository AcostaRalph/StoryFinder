package csc413_team19.storyfinder;

import java.util.UUID;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StoryActivity extends SingleFragmentActivity {

    private static final String EXTRA_STORY_ID = "csc413_team19.storyfinder.story_id";

    @Override
    protected Fragment createFragment() {
        UUID storyId = (UUID) getIntent().getSerializableExtra(EXTRA_STORY_ID);
        return StoryFragment.newInstance(storyId);
    }

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext, StoryActivity.class);
        intent.putExtra(EXTRA_STORY_ID, id);
        return intent;
    }

}
