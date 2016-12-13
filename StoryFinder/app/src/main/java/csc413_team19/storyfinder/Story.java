package csc413_team19.storyfinder;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by racosta on 11/26/16.
 */

public class Story {

    private UUID mUUID;
    private String mID;
    private String mName;
    private String mDescription;
    private Drawable mImage;

    public Story(JSONObject jsonObject) throws JSONException{
        JSONObject jsonShowObject = jsonObject.getJSONObject("show");
        JSONObject jsonExternalsObject = jsonShowObject.getJSONObject("externals");
        if(jsonShowObject.has("name"))
//            this.setName(jsonObject.getString("name"));
        this.setName(jsonShowObject.getString("name"));
//        if(jsonShowObject.has("summary"))
//            this.setDescription(jsonShowObject.getString("summary"));
        this.setID(jsonExternalsObject.getString("imdb"));
    }


    public Story() {
        mUUID = UUID.randomUUID();
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        mImage = image;
    }
}
