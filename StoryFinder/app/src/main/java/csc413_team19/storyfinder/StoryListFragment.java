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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


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
//        mStorySearchView.setSubmitButtonEnabled(true);
//        mStorySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
//            @Override
//            public boolean onQueryTextSubmit(String query){
//                //mAdapter.getFilter().filter(query);
//                return false;
//            }
//
//
//            @Override
//            public boolean onQueryTextChange(String query){
//                //mAdapter.getFilter().filter(query);
//                return false;
//            }
//        });

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
        private TextView mDescriptionTextView;
        private ImageView mPhotoImageView;

        private Story mStory;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.card_story_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.card_story_description);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.card_image);
        }

        public void bindStory(Story story) {
            mStory = story;
            mTitleTextView.setText(mStory.getName());
            mDescriptionTextView.setText(mStory.getDescription());
//            mPhotoImageView.setImageDrawable(mStory.getImage());
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

        void setDescription(String description){
            String desc = description;
            this.mDescriptionTextView.setText(desc);
        }

        void setID(String id){
            String ID = id;

        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> /*implements Filterable*/ {

        Context mContext;
        private ArrayList<Story> mStories;
        private View.OnClickListener mListener;
        //private ArrayList<Story> mFilterList;
        //SearchFilter filter;

        public StoryAdapter(Context context, ArrayList<Story> stories) {
            this.mContext = context;
            this.mStories = stories;
            //this.mFilterList = stories;
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
//            storyHolder.setDescription(card.getDescription());
//            storyHolder.setID(card.getID());
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



        //Filtering of list starts here
//        @Override
//        public Filter getFilter() {
//            if(filter == null){
//                filter = new SearchFilter(mFilterList, this);
//            }
//            return filter;
//        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 0) {
            mController.cancelAllRequests();
            mController.sendRequest(newText);
        } else if(newText.equals("")) {
//            mStoryRecyclerView.removeAllViewsInLayout();
            mAdapter.mStories.clear();
            mAdapter.notifyDataSetChanged();
//            mStoryRecyclerView.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
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
//            mStoryRecyclerView.removeAllViewsInLayout();
            mAdapter.mStories.clear();
            mAdapter.notifyDataSetChanged();
//            Toast.makeText(MainActivity.this, "Must provide more than one character", Toast.LENGTH_SHORT).show();
//            mStoryRecyclerView.setVisibility(View.GONE);
//            textView.setVisibility(View.VISIBLE);
//            textView.setText("Must provide more than one character to search");
            return true;
        }
    }



//    private class SearchFilter extends Filter {
//
//        //Local containers
//        StoryAdapter adapter;
//        ArrayList<Story> filterList;
//
//        //Sets local containers equal to passed values
//        private SearchFilter(ArrayList<Story> filterList, StoryAdapter adapter){
//            this.adapter = adapter;
//            this.filterList = filterList;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence arg){
//            FilterResults results = new FilterResults();
//
//            if(arg != null && arg.length() > 0){
//                arg = arg.toString().toUpperCase();
//
//                ArrayList<Story> filteredStory = new ArrayList<>();
//
//                for(int i = 0; i < filterList.size(); i++){
//                    if(filterList.get(i).getName().toUpperCase().contains(arg)){
//                        filteredStory.add(filterList.get(i));
//                    }
//                }
//                results.count=filteredStory.size();
//                results.values=filteredStory;
//            }else{
//                results.count=filterList.size();
//                results.values=filterList;
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence arg, Filter.FilterResults results){
//            adapter.mStories = (ArrayList<Story>) results.values;
//
//            adapter.notifyDataSetChanged();
//        }
//    }

}
