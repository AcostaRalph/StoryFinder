package csc413_team19.storyfinder;

import java.util.UUID;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class StoryActivity extends AppCompatActivity {

    private static final String EXTRA_STORY_ID = "csc413_team19.storyfinder.story_id";

    public static String sSearchToken;

//    @Override
//    protected Fragment createFragment() {
//        String storyId = (String) getIntent().getSerializableExtra(EXTRA_STORY_ID);
//        return StoryFragment.newInstance(storyId);
//    }

    public static Intent newIntent(Context packageContext, String id) {
        Intent intent = new Intent(packageContext, StoryActivity.class);
        sSearchToken = id;
        intent.putExtra(EXTRA_STORY_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        String storyId = (String) getIntent().getSerializableExtra(EXTRA_STORY_ID);
//        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        StoryFragment storyFragment = StoryFragment.newInstance(storyId);
//        storyFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_story, storyFragment).commit();
    }
}
