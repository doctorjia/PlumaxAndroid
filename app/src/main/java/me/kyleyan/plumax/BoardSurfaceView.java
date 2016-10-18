package me.kyleyan.plumax;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class BoardSurfaceView extends SurfaceView {

    private Bitmap bmpBackground;
    public BoardSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        SurfaceHolder holder = getHolder();
        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.simple_board);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas(null);
                int size = Math.min(canvas.getWidth(), canvas.getHeight());
                bmpBackground = Bitmap.createScaledBitmap(
                        bmpBackground, size, size, false);
                drawSomething(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    protected void drawSomething(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bmpBackground, (canvas.getWidth() - bmpBackground.getWidth())/2, (canvas.getHeight() - bmpBackground.getHeight())/2, null);
    }
}
