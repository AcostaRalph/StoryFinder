package csc413_team19.storyfinder;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;

import java.util.UUID;

/**
 * Created by racosta on 11/26/16.
 */

public class Story {

    private UUID mUUID;
    private String mName;
    private String mDescription;
    private Drawable mImage;

    public Story() {
        mUUID = UUID.randomUUID();
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
