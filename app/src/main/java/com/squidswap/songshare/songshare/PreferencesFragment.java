package com.squidswap.songshare.songshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PreferencesFragment extends Fragment {
    private Button Logout;
    private SharedPreferences prefs;
    private AlertDialog.Builder dialog;

    public PreferencesFragment() {

    }

    public static PreferencesFragment newInstance(String param1, String param2) {
        PreferencesFragment fragment = new PreferencesFragment();

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
        View v = inflater.inflate(R.layout.fragment_preferences, container, false);
        Logout = (Button) v.findViewById(R.id.LogoutButton);
        dialog = new AlertDialog.Builder(getActivity());

        dialog.setMessage("Are you sure you would like to log out?").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor edit = prefs.edit();

                edit.putInt("logged-in",0);
                edit.remove("user-id");
                edit.commit();

                Intent i = new Intent(getActivity(),LoginScreen.class);
                startActivity(i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        InitListeners();
        return v;
    }

    //Initialize all the different element listeners.
    private void InitListeners(){
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        prefs = getActivity().getSharedPreferences("songshare-prefs",Context.MODE_PRIVATE);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
