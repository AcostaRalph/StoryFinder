package csc413_team19.storyfinder;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.ArrayList;

/**
 * Created by racosta on 12/11/16.
 */

public class JsonController {

    private final int TAG = 100;

    private OnResponseListener mResponseListener;

    public JsonController(OnResponseListener responseListener) {
        mResponseListener = responseListener;
    }

    public void sendRequest(String query){

        int method = Request.Method.GET;

        String url = "http://api.tvmaze.com/search/shows?q=" + Uri.encode(query);

        JsonRequest request = new JsonRequest(method, url,
                new Response.Listener<ArrayList<Story>>() {
                    @Override
                    public void onResponse(ArrayList<Story> response) {
                        mResponseListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseListener.onFailure(error.getMessage());
                    }
                }
        );

        request.setTag(TAG);

        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void sendSingleRequest(String query){

        int method = Request.Method.GET;
        String url = "http://api.tvmaze.com/lookup/shows?imdb=" + Uri.encode(query);

        JsonSingleRequest request = new JsonSingleRequest(method, url,
                new Response.Listener<ArrayList<Story>>() {
                    @Override
                    public void onResponse(ArrayList<Story> response) {
                        mResponseListener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mResponseListener.onFailure(error.getMessage());
                    }
                }
        );

        request.setTag(TAG);

        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void cancelAllRequests(){
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener {
        void onSuccess(ArrayList<Story> stories);
        void onFailure(String errorMessage);
    }
}
