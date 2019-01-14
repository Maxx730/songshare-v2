package com.squidswap.songshare.songshare;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import NetworkServices.NetworkResponseInterface;
import NetworkServices.UserServices;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ProfileFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private UserServices UserServ;
    private SharedPreferences prefs;
    private ImageView SmallProfile,LargeProfile;
    private TextView UsernameDisplay,JoinDate;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserServ = new UserServices(getActivity().getApplicationContext(), new NetworkResponseInterface() {
            @Override
            public void OnResponse(JSONObject response) {
            try{
                String url = response.getString("profile");
                String name = response.getString("username");

                Glide.with(getActivity().getApplicationContext()).load(url).into(SmallProfile);
                Glide.with(getActivity().getApplicationContext()).load(url).apply(RequestOptions.bitmapTransform(new BlurTransformation(25,5))).into(LargeProfile);
                UsernameDisplay.setText(name);
            }catch(JSONException e){
                Log.d("PROFILE FRAGMENT :::: ","Error grabbing date...");
                e.printStackTrace();
            }
            }
        });

        prefs = getActivity().getSharedPreferences("songshare-prefs",Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        SmallProfile = (ImageView) v.findViewById(R.id.SmallProfileImage);
        LargeProfile = (ImageView) v.findViewById(R.id.LargeProfileImage);
        UsernameDisplay = (TextView) v.findViewById(R.id.UsernameDisplay);
        JoinDate = (TextView) v.findViewById(R.id.JoinDate);

        UserServ.GetUser(prefs.getInt("user-id",0));
        return v;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
