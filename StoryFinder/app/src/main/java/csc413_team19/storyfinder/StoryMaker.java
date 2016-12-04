package csc413_team19.storyfinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by racosta on 11/26/16.
 */

public class StoryMaker extends AppCompatActivity{

    private static StoryMaker sStoryMaker;

    private ArrayList<Story> mStories;

    public static StoryMaker get(Context context) {
        if (sStoryMaker == null) {
            sStoryMaker = new StoryMaker(context);
        }
        return sStoryMaker;
    }

    private StoryMaker(Context context) {

        mStories = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            Story story = new Story();
            switch(i%10){
                case 1: story.setName(i + "st Tale");
                    break;
                case 2: story.setName(i + "nd Tale");
                    break;
                case 3: story.setName(i + "rd Tale");
                    break;
                default: story.setName(i + "th Tale");
                    break;

            }
            if(i == 11 || i == 12 || i == 13)
                story.setName(i + "th Tale");

            story.setDescription("Description for story " + i);
            if(i%3 == 0) {
                story.setImage(context.getDrawable(R.drawable.hyrule));
            }else if(i%3 == 1){
                story.setImage(context.getDrawable(R.drawable.majora));
            }else{
                story.setImage(context.getDrawable(R.drawable.triforce));
            }
            mStories.add(story);
        }
    }

    public ArrayList<Story> getStories() {
        return mStories;
    }

    public Story getStory(UUID id) {
        for (Story story : mStories) {
            if (story.getUUID().equals(id)) {
                return story;
            }
        }
        return null;
    }
}
