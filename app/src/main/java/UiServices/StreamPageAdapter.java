package UiServices;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class StreamPageAdapter extends FragmentPagerAdapter {
    public StreamPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new TrackFragment();
    }

    @Override
    public int getCount() {
        return 6;
    }
}
