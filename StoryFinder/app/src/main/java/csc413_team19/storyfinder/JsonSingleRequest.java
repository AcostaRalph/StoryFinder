package csc413_team19.storyfinder;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by racosta on 12/14/16.
 */

public class JsonSingleRequest extends Request<ArrayList<Story>> {

    private Response.Listener<ArrayList<Story>> successListener;

    public JsonSingleRequest( int method, String url, Response.Listener<ArrayList<Story>> successListener,
                        Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<ArrayList<Story>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        ArrayList<Story> stories = new ArrayList<>();
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);

        try {
//                jsonObject = new JSONObject(jsonString);
            jsonObject = new JSONObject(jsonString);
            stories.add(StoryMaker.parseJson(jsonObject));
        } catch (JSONException excpetion) {
            excpetion.printStackTrace();
            return Response.error(new VolleyError("Request Failed"));
        }


        return Response.success(stories, getCacheEntry());
    }

    @Override
    protected void deliverResponse(ArrayList<Story> response) {
        successListener.onResponse(response);
    }
}
