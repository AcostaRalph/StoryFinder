package csc413_team19.storyfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


public class StoryListFragment extends Fragment
        implements SearchView.OnQueryTextListener {

    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdapter;
    private SearchView mStorySearchView;
    JsonController mController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storylist_list, container, false);
        //Lets action bar actions to be used
        setHasOptionsMenu(true);
        //Instantiates the use of search bar
        mStorySearchView = (SearchView) view.findViewById(R.id.action_search);

        mStoryRecyclerView = (RecyclerView) view
                .findViewById(R.id.story_recycler_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        mStoryRecyclerView.setAdapter(mAdapter);
//        StoryMaker storyMaker = StoryMaker.get(getActivity());
//        ArrayList<Story> stories = storyMaker.getStories();
//        mAdapter = new StoryAdapter(getContext(), stories);

        mController = new JsonController(new JsonController.OnResponseListener() {
            @Override
            public void onSuccess(ArrayList<Story> stories) {
                mAdapter.updateDataset(stories);
                mStoryRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String errorMessage) {
//                Toast.makeText(StoryListActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });



        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        //Lets search bar in appbar be used
        MenuItem search = menu.findItem(R.id.action_search);
        //Listens for search bar changes
        mStorySearchView = (SearchView) MenuItemCompat.getActionView(search);
        mStorySearchView.setOnQueryTextListener(this);
        mStorySearchView.setIconifiedByDefault(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateUI() {
        StoryMaker storyMaker = StoryMaker.get(getActivity());
        ArrayList<Story> stories = storyMaker.getStories();
        if(mAdapter == null) {
            mAdapter = new StoryAdapter(getContext(), stories);
            mStoryRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class StoryHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mRatingText;
        private NetworkImageView mPhotoImageView;
        private RatingBar mRatingBar;
        private Story mStory;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.card_story_title);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.card_ratingBar);
            mPhotoImageView = (NetworkImageView) itemView.findViewById(R.id.card_image);
            mRatingText = (TextView) itemView.findViewById(R.id.card_rating);
            mRatingBar.setNumStars(10);
            mRatingBar.setIsIndicator(true);
        }

        public void bindStory(Story story) {
            mStory = story;
            ImageLoader imageLoader =
                    VolleySingleton.getInstance(App.getContext()).getImageLoader();
            mTitleTextView.setText(mStory.getName());

            if(mStory.getRating() == 0)
                mRatingText.setText("No Rating Available");
            else
                mRatingText.setText("Average Rating: " + mStory.getRating());

            mRatingBar.setRating(mStory.getRating());
            mPhotoImageView.setImageUrl(mStory.getPictureUrl(), imageLoader);

        }

        @Override
        public void onClick(View v) {
            Intent intent = StoryActivity.newIntent(getActivity(), mStory.getID());
            startActivity(intent);
        }

        void setTitle(String title){
            String name = title;
            this.mTitleTextView.setText(name);
        }

        void setPhotoImageURL(String imageURL){
            ImageLoader imageLoader =
                    VolleySingleton.getInstance(App.getContext()).getImageLoader();
            this.mPhotoImageView.setImageUrl(imageURL, imageLoader);
        }

        void setRate(double rating){
            float rate = (float)rating;
            this.mRatingBar.setRating(rate);
        }
        void setID(String id){
            String ID = id;

        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> /*implements Filterable*/ {

        Context mContext;
        private ArrayList<Story> mStories;
        private View.OnClickListener mListener;

        public StoryAdapter(Context context, ArrayList<Story> stories) {
            this.mContext = context;
            this.mStories = stories;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_storylist, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryHolder holder, int position) {
            Story card = mStories.get(position);
            holder.bindStory(card);
//            StoryHolder storyHolder = (StoryHolder) holder;
//            storyHolder.setTitle(card.getName());
//            storyHolder.setRate(card.getRating());
//            storyHolder.setPhotoImageURL(card.getPictureUrl());
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }

        public void updateDataset(ArrayList<Story> storyList){
            this.mStories.clear();
            this.mStories.addAll(storyList);
            notifyDataSetChanged();
        }

        public void setListener(View.OnClickListener listener){
            this.mListener = listener;
        }

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 0) {
            mController.cancelAllRequests();
            mController.sendRequest(newText);
        } else if(newText.equals("")) {
            mAdapter.mStories.clear();
            mAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.length() > 0) {
            mController.cancelAllRequests();
            mController.sendRequest(query);
            return false;
        } else {
            mAdapter.mStories.clear();
            mAdapter.notifyDataSetChanged();
            return true;
        }
    }

}
