package com.example.cannongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CannonView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int BALL_SPEED = 300;
    public static final int BALL_RADIUS = 15;

    private int screenWidth;
    private int screenHeight;
    private boolean isPlaying = false;
    private SurfaceHolder holder;
    private Paint paint;

    public CannonView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isPlaying = true;
        // Aqui o jogo poderá iniciar a thread de renderização futuramente
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isPlaying = false;
        while (retry) {
            try {
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}