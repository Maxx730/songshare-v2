package UiServices;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import com.squidswap.songshare.songshare.R;

import java.util.ArrayList;

public class LoginCanvas extends View {
    private Paint SinPaint;
    private DisplayMetrics Display;
    private int width,height,stroke;
    private ArrayList<MusicLine> lines;

    public LoginCanvas(Context context) {
        super(context);

        lines = new ArrayList<>();
        width = context.getResources().getDisplayMetrics().widthPixels;
        height = context.getResources().getDisplayMetrics().heightPixels;
        stroke = width / 30;

        SinPaint = new Paint();
        SinPaint.setColor(getResources().getColor(R.color.gray_1));
        SinPaint.setStrokeWidth(stroke);
        SinPaint.setAntiAlias(true);
        SinPaint.setStyle(Paint.Style.STROKE);
        SinPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private class MusicLine{
        public int height,maxHeight,minHeight;
        public boolean up = true;
        public float speed;

        public MusicLine(int height,int max,int min,float speed){
            this.height = height;
            this.maxHeight = max;
            this.minHeight = min;
            this.speed = speed;
        }
    }
}
