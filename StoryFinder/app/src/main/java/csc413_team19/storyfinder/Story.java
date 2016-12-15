package csc413_team19.storyfinder;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import org.json.JSONArray;
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
    private CharSequence mDescription;
    private String mPictureUrl;
    private float mRating;
    private String mLanguage;
    private String mGenre = "";
    private int mRuntime;

    public Story(JSONObject jsonObject) throws JSONException{
        JSONObject jsonShowObject;
        if(jsonObject.has("show")) {
            jsonShowObject = jsonObject.getJSONObject("show");
        }else{
            jsonShowObject = jsonObject;
        }
        JSONObject jsonExternalsObject = jsonShowObject.getJSONObject("externals");
        JSONObject jsonImageObject = jsonShowObject.getJSONObject("image");
        JSONObject jsonRatingObject = jsonShowObject.getJSONObject("rating");
        JSONArray jsonGenreArray = jsonShowObject.getJSONArray("genres");

        this.setName(jsonShowObject.getString("name"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.setDescription(Html.fromHtml(jsonShowObject.getString("summary"), Html.FROM_HTML_MODE_LEGACY));
        }
        else{
            this.setDescription(Html.fromHtml(jsonShowObject.getString("summary")));
        }

        this.setID(jsonExternalsObject.getString("imdb"));

        try{
            this.setPictureUrl(jsonImageObject.getString("original"));
        }catch(JSONException e){
            e.printStackTrace();
            this.setPictureUrl("https://pvsmt99345.i.lithium.com/t5/image/serverpage/image-id/10546i3DAC5A5993C8BC8C?v=v2");
        }

        try {
            this.setRating((float)jsonRatingObject.getDouble("average"));
        }catch(JSONException e){
            e.printStackTrace();
            this.setRating(0.0f);
        }

        try {
            this.setRuntime(jsonShowObject.getInt("runtime"));
        }
        catch(JSONException e){
            this.setRuntime(0);
            e.printStackTrace();
        }
        try {
            this.setLanguage(jsonShowObject.getString("language"));
        }
        catch(JSONException e){
            e.printStackTrace();
            this.setLanguage("No Language Data Available");
        }
        try {
            if(jsonGenreArray.length() != 0) {
                for (int i = 0; i < jsonGenreArray.length(); i++) {
                    this.setGenre(jsonGenreArray.getString(i));
                }
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public CharSequence getDescription() {
        return mDescription;
    }

    public void setDescription(Spanned description) {
        mDescription = description;
    }


    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int runtime) {
        mRuntime = runtime;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = mGenre + " | " + genre;
    }

    public String getLanguage() {

        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }
}
