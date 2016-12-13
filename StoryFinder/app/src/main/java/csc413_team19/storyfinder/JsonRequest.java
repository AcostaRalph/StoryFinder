package csc413_team19.storyfinder;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by racosta on 12/11/16.
 */

public class JsonRequest extends Request<ArrayList<Story>> {

    private Response.Listener<ArrayList<Story>> successListener;

    public JsonRequest( int method, String url, Response.Listener<ArrayList<Story>> successListener,
                        Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<ArrayList<Story>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        ArrayList<Story> stories = new ArrayList<Story>();
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
//                jsonObject = new JSONObject(jsonString);
                jsonObject = jsonArray.getJSONObject(i);
                stories.add(StoryMaker.parseJson(jsonObject));
            } catch (JSONException excpetion) {
                excpetion.printStackTrace();
                return Response.error(new VolleyError("Request Failed"));
            }

        }
            return Response.success(stories, getCacheEntry());
    }

    @Override
    protected void deliverResponse(ArrayList<Story> response) {
        successListener.onResponse(response);
    }
}
