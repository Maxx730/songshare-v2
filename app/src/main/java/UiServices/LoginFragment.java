package UiServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squidswap.songshare.songshare.MainFragmentManager;
import com.squidswap.songshare.songshare.R;

import org.json.JSONException;
import org.json.JSONObject;

import NetworkServices.LoginService;
import NetworkServices.NetworkResponseInterface;

public class LoginFragment extends Fragment {
    private EditText LoginUsername,LoginPassword;
    private Button LoginButton;
    private LoginService log;
    private SharedPreferences shared;
    private ProgressBar Loading;
    private TextView SignUpScroll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);

        LoginUsername = (EditText) v.findViewById(R.id.LoginUsernameField);
        LoginPassword = (EditText) v.findViewById(R.id.LoginPasswordField);
        LoginButton = (Button) v.findViewById(R.id.LoginButton);
        shared = getActivity().getSharedPreferences("songshare-prefs",Context.MODE_PRIVATE);
        Loading = getActivity().findViewById(R.id.LoginLoading);
        SignUpScroll = (TextView) v.findViewById(R.id.SignUpScrollText);

        log = new LoginService(getActivity().getApplicationContext(), new NetworkResponseInterface() {
            @Override
            public void OnResponse(JSONObject response) {
                Handler handle = new Handler();

                try{
                    if(response.getString("TYPE").equals("SUCCESS")){
                        //Since there was a success we want to write the user id and logged in status to the
                        //songshare sprefs.
                        SharedPreferences.Editor edit = shared.edit();

                        edit.putInt("logged-in",1);
                        edit.putInt("user-id",response.getJSONObject("PAYLOAD").getInt("_id"));
                        edit.commit();

                        Intent i = new Intent(getActivity().getApplicationContext(),MainFragmentManager.class);
                        startActivity(i);
                    }else{

                    }

                    //Show the loading animation once the login button has been pressed regardless
                    //of whether or not the user has passed credentials or not.
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Loading.setVisibility(View.GONE);
                        }
                    },1000);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });

        SetListeners();

        return v;
    }

    private void SetListeners(){
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.setVisibility(View.VISIBLE);
                log.CheckLogin(LoginUsername.getText().toString(),LoginPassword.getText().toString());
            }
        });

        SignUpScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager view =  (ViewPager) getActivity().findViewById(R.id.LoginPager);
                view.setCurrentItem(1);
            }
        });
    }
}
