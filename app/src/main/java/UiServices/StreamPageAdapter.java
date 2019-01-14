package UiServices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

public class StreamPageAdapter extends FragmentPagerAdapter {
    private JSONArray tracks;

    public StreamPageAdapter(FragmentManager fm,JSONArray tracks) {
        super(fm);
        this.tracks = tracks;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle b = new Bundle();
        TrackFragment frag = new TrackFragment();

        try{
            b.putString("track_data",tracks.get(i).toString());
            frag.setArguments(b);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return frag;
    }

    @Override
    public int getCount() {
        return tracks.length();
    }
}
