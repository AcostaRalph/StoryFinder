package csc413_team19.storyfinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by racosta on 11/26/16.
 */

public class StoryMaker extends AppCompatActivity{

    private static StoryMaker sStoryMaker;

    private static ArrayList<Story> mStories;

    public static StoryMaker get(Context context) {
        if (sStoryMaker == null) {
            sStoryMaker = new StoryMaker(context);
        }
        return sStoryMaker;
    }

    private StoryMaker(Context context) {

        mStories = new ArrayList<>();
        Story story = new Story();

//        story.setName("");
//        story.setDescription("");

//        mStories.add(story);

    }

    public static Story parseJson(JSONObject jsonObject) throws JSONException {
//        ArrayList<Story> movies = new ArrayList<>();
        Story story = new Story(jsonObject);
        // Check if the JSONObject has object with key "Search"
//        if(jsonObject.has("Search")){
//            // Get JSONArray from JSONObject
//            JSONArray jsonArray = jsonObject.getJSONArray("");
//            for(int i = 0; i < jsonArray.length(); i++){
//                // Create new Movie object from each JSONObject in the JSONArray
//                movies.add(new Story(jsonArray.getJSONObject(i)));
//            }
//        }
//        sStories = movies;
        mStories.add(story);
//        return movies;
        return story;
    }

    public ArrayList<Story> getStories() {
        return mStories;
    }

    public Story getStory(String id) {
        for (Story story : mStories) {
            if (story.getID().equals(id)) {
                return story;
            }
        }
        return null;
    }
}
