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

import java.util.UUID;


public class StoryFragment extends Fragment {

    private static final String ARG_STORY_ID = "story_id";

    private Story mStory;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mPicture;


    public static StoryFragment newInstance(UUID storyId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);

        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryMaker.get(getActivity()).getStory(storyId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mTitle = (TextView) v.findViewById(R.id.titleTextView);
        mDescription = (TextView) v.findViewById(R.id.descTextView);
        mPicture = (ImageView) v.findViewById(R.id.storyImageView);

        mTitle.setText(mStory.getName());
        mDescription.setText(mStory.getDescription());
        mPicture.setImageDrawable(mStory.getImage());

        return v;
    }



}