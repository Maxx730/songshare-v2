package UiServices;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squidswap.songshare.songshare.R;
import com.squidswap.songshare.songshare.TrackDetails;

import org.json.JSONException;
import org.json.JSONObject;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class TrackFragment extends Fragment {

    private JSONObject data;
    private ValueAnimator SlideUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getArguments().containsKey("track_data")){
            try{
                data = new JSONObject(this.getArguments().getString("track_data"));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.track_fragment,container,false);
        ImageView Art = v.findViewById(R.id.TrackAlbumArt);
        ImageView SmallArt = v.findViewById(R.id.SmallArtwork);
        TextView TrackTitle = v.findViewById(R.id.TrackTitle);
        TextView TrackArtist = v.findViewById(R.id.TrackArtist);
        TextView SharedBy = v.findViewById(R.id.SharedBy);

        try{
            Glide.with(getActivity().getApplicationContext()).load(data.getString("art")).apply(RequestOptions.bitmapTransform(new BlurTransformation(25,5))).into(Art);
            Glide.with(getActivity().getApplicationContext()).load(data.getString("art")).into(SmallArt);
            TrackTitle.setText(data.getString("title"));
            SharedBy.setText(SharedBy.getText().toString() + " " + data.getString("username"));

            if(data.getString("artist").equals("")){
                TrackArtist.setText("Anonymous");
            }else{
                TrackArtist.setText(data.getString("artist"));
            }

            DisplayMetrics met = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(met);
            SlideUp = ValueAnimator.ofInt(0,(int) (met.heightPixels / 3) * 2);

            SlideUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int val = (int) animation.getAnimatedValue();
                    ViewGroup.LayoutParams lay = getActivity().findViewById(R.id.DetailsCard).getLayoutParams();
                    lay.height = val;
                    getActivity().findViewById(R.id.DetailsCard).setLayoutParams(lay);
                }
            });

            Art.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().findViewById(R.id.MenuShade).setVisibility(View.VISIBLE);
                    SlideUp.start();
                }
            });

            SmallArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }catch(JSONException e){
            e.printStackTrace();
        }

        return v;
    }
}