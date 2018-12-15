package UiServices;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squidswap.songshare.songshare.R;

public class StreamViewMusicFragment extends Fragment {

    private static ViewPager TrackPager;
    private TrackPageAdapter TrackAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.streamview_music_stream,container,false);

        //Setup sub track fragment adapter
        TrackPager = (ViewPager) v.findViewById(R.id.TrackPager);
        TrackAdapter = new TrackPageAdapter(getActivity().getSupportFragmentManager());

        TrackPager.setAdapter(TrackAdapter);

        return v;
    }
}
