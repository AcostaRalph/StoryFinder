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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;


public class StoryFragment extends Fragment {

    private static final String ARG_STORY_ID = "story_id";

    private Story mStory;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mPicture;
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
        String storyId = (String) getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryMaker.get(getActivity()).getStory(storyId);
        sSearchToken = storyId;
//        mController = new JsonController(new JsonController.OnResponseListener() {
//            @Override
//            public void onSuccess(ArrayList<Story> stories) {
//                mStory = stories.get(0);
//            }
//            @Override
//            public void onFailure(String errorMessage) {
//
//            }
//        });
//        mController.sendSingleRequest(sSearchToken);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitle = (TextView) v.findViewById(R.id.titleTextView);
        mDescription = (TextView) v.findViewById(R.id.descTextView);
        mPicture = (ImageView) v.findViewById(R.id.storyImageView);



//        StoryMaker storyMaker = StoryMaker.get(getContext());
//        ArrayList<Story> stories = storyMaker.getStories();

        mTitle.setText(mStory.getName());
        mDescription.setText(mStory.getID());
//        mTitle.setText(stories.get(0).getName());
//        mDescription.setText(stories.get(0).getID());
//        mPicture.setImageDrawable(mStory.getImage());

        return v;
    }



}