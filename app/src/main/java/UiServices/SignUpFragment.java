package UiServices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squidswap.songshare.songshare.R;

public class SignUpFragment extends Fragment {

    private TextView BackText,Terms;
    private EditText SignUpUsername,SignUpPassword,SignUpRepeat,SignUpEmail;
    private CheckBox EULACheck;
    private ViewPager view;
    private AlertDialog.Builder EULADialog;
    private Button SignUpButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_up_fragment,container,false);
        Terms = (TextView) v.findViewById(R.id.TermsOfService);
        BackText = (TextView) getActivity().findViewById(R.id.LoginBack);
        view = (ViewPager) getActivity().findViewById(R.id.LoginPager);

        EULACheck = (CheckBox) v.findViewById(R.id.EULACheckbox);
        SignUpUsername = (EditText) v.findViewById(R.id.SignUpUsername);
        SignUpPassword = (EditText) v.findViewById(R.id.SignUpPassword);
        SignUpRepeat = (EditText) v.findViewById(R.id.SignUpPasswordRepeat);
        SignUpEmail = (EditText) v.findViewById(R.id.SignUpEmail);
        SignUpButton = (Button) v.findViewById(R.id.SignUpButton);

        EULADialog = new AlertDialog.Builder(getActivity());
        EULADialog.setMessage(R.string.eula_text).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        InitClickListeners();

        return v;
    }

    public void InitClickListeners(){
        Terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EULADialog.create().show();
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SignUpUsername.getText().toString().equals("") && !SignUpPassword.getText().toString().equals("") && SignUpPassword.getText().toString().equals(SignUpRepeat.getText().toString())){

                }
            }
        });
    }
}
