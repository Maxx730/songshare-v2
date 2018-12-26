package com.squidswap.songshare.songshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private static ViewPager StreamPager;
    private StreamPageAdapter StreamAdapter;

    public StreamFragment() {
        // Required empty public constructor
    }

    public static StreamFragment newInstance(String param1, String param2) {
        StreamFragment fragment = new StreamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.stream_screen, container, false);

        StreamPager = (ViewPager) layout.findViewById(R.id.TrackPager);
        StreamAdapter = new StreamPageAdapter(getActivity().getSupportFragmentManager());

        StreamPager.setOffscreenPageLimit(3);
        StreamPager.setPageTransformer(true, new TrackTransformer());
        StreamPager.setAdapter(StreamAdapter);

        return layout;
    }

    public void RefreshPager(){
        StreamPager.setAdapter(StreamAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
