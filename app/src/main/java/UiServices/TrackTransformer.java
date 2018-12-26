package UiServices;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.squidswap.songshare.songshare.R;

public class TrackTransformer implements ViewPager.PageTransformer {

    private float MIN_SCALE_FACTOR = 0.2f;

    @Override
    public void transformPage(@NonNull final View page, float position) {
        TextView t = (TextView) page.findViewById(R.id.FragmentPosition);
        t.setText(String.valueOf(position));

        //Scale down every card.
        page.setScaleX(0.9f);
        page.setScaleY(0.9f);

        if(position > 0 || position < 0){
            if(position < 0){
                float scale = page.getScaleX() + position;
                if(position < 0 && scale > .6f){
                    page.setScaleX(page.getScaleX() + position);
                    page.setScaleY(page.getScaleY() + position);
                }else{
                    page.setScaleX(.6f);
                    page.setScaleY(.6f);
                }
            }else{
                float scale = page.getScaleX() - position;
                if(position > 0 && scale > .6f){
                    page.setScaleX(page.getScaleX() - position);
                    page.setScaleY(page.getScaleY() - position);
                }else{
                    page.setScaleX(.6f);
                    page.setScaleY(.6f);
                }
            }
        }
    }
}
