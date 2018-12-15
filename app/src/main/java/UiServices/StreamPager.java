package UiServices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

//Overrides ViewPager to disable touch events so we can set the
//events on the bottom buttons.
public class StreamPager extends ViewPager {
    public StreamPager(@NonNull Context context) {
        super(context);
    }
}
