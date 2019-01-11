package UiServices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.squidswap.songshare.songshare.StreamFragment;

public class LoginPageAdapter extends FragmentPagerAdapter {
    public LoginPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("LOGIN PAGE ADAPTER ::: ",String.valueOf(i));

        if(i == 1){
            return new SignUpFragment();
        }else{
            return new LoginFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
