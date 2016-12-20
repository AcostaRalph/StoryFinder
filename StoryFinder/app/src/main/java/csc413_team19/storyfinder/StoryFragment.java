package csc413_team19.storyfinder;

import android.content.Context;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.UUID;


public class StoryFragment extends Fragment {

    private static final String ARG_STORY_ID = "story_id";

    private Story mStory;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mLanguage;
    private TextView mGenre;
    private TextView mRuntime;
    private TextView mRating;
    private NetworkImageView mStoryImage;
    static String sSearchToken;
    JsonController mController;


    public static StoryFragment newInstance(String storyId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);

        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String storyId = (String) getArguments().getSerializable(ARG_STORY_ID);
        sSearchToken = storyId;
        mController = new JsonController(new JsonController.OnResponseListener() {
            @Override
            public void onSuccess(ArrayList<Story> stories) {
                mStory = stories.get(0);
                updateDataSet(mStory);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitle = (TextView) v.findViewById(R.id.titleTextView);
        mDescription = (TextView) v.findViewById(R.id.descTextView);
        mStoryImage = (NetworkImageView) v.findViewById(R.id.storyImageView);
        mLanguage = (TextView) v.findViewById(R.id.language_label);
        mGenre = (TextView) v.findViewById(R.id.genre_label);
        mRuntime = (TextView) v.findViewById(R.id.runtime_label);
        mRating = (TextView) v.findViewById(R.id.rating_label);
        StoryMaker storyMaker = StoryMaker.get(getContext());
        mController.sendSingleRequest(sSearchToken);

        return v;
    }

    public void updateDataSet(Story story){
        ImageLoader imageLoader =
                VolleySingleton.getInstance(App.getContext()).getImageLoader();

        mTitle.setText(story.getName());

        mDescription.setText(story.getDescription());

        mStoryImage.setImageUrl(story.getPictureUrl(), imageLoader);
        mLanguage.setText("Language: " + story.getLanguage());
        if(mStory.getGenre() == ""){
            mGenre.setText("No Genre Available");
        }else {
            mGenre.setText(mStory.getGenre() + " |");
        }
        if(mStory.getRuntime() == 0)
            mRuntime.setText("No Runtime available");
        else
            mRuntime.setText("Runtime: " + Integer.toString(story.getRuntime()) + " min");

        if(mStory.getRating() == 0)
            mRating.setText("No Rating Available");
        else
            mRating.setText("Average Rating: " + story.getRating());

    }

}