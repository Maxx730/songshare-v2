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
        switch(i){
            case 0:
                return new StreamViewProfileFragment();
            case 1:
                return new StreamViewMusicFragment();
            default:
                return new StreamViewMusicFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
