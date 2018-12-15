package UiServices;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class StreamPageAdapter extends FragmentPagerAdapter {
    public StreamPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Log.d("STREAM PAGE ADAPTER","Current Page: " +  String.valueOf(i));
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
