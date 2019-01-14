package com.squidswap.songshare.songshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import NetworkServices.NetworkResponseInterface;
import NetworkServices.TrackService;
import UiServices.StreamPageAdapter;
import UiServices.TrackTransformer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StreamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StreamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StreamFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private static ViewPager StreamPager;
    private StreamPageAdapter StreamAdapter;
    private TrackService tracks;
    private ProgressBar Loading;

    public StreamFragment() {
    }

    public static StreamFragment newInstance(String param1, String param2) {
        StreamFragment fragment = new StreamFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.stream_screen, container, false);
        Loading = (ProgressBar) layout.findViewById(R.id.LoadingStreams);

        tracks = new TrackService(getActivity().getApplicationContext(), new NetworkResponseInterface() {
            @Override
            public void OnResponse(JSONObject response) {
                try{
                    Loading.setVisibility(View.GONE);
                    JSONArray tracks = response.getJSONArray("PAYLOAD");
                    StreamPager = (ViewPager) layout.findViewById(R.id.TrackPager);
                    StreamAdapter = new StreamPageAdapter(getChildFragmentManager(),tracks);

                    StreamPager.setOffscreenPageLimit(3);
                    StreamPager.setPageTransformer(true, new TrackTransformer());
                    StreamPager.setAdapter(StreamAdapter);
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) StreamPager.getLayoutParams();
                }catch(JSONException e){
                    Log.d("STREAM FRAGMENT :::: ","Error grabbing shared tracks...");
                    e.printStackTrace();
                }
            }
        });

        tracks.PullStream(1);
        //Once the fragment has been initialized, we want to load all the shared streams for
        //the given user.


        return layout;
    }

    public void RefreshPager(){
        StreamPager.setAdapter(StreamAdapter);
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
