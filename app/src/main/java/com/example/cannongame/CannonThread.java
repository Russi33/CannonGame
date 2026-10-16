package com.example.cannongame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CannonThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private final CannonView cannonView;
    private boolean isRunning = false;

    public CannonThread(SurfaceHolder holder, CannonView view) {
        this.surfaceHolder = holder;
        this.cannonView = view;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    @Override
    public void run() {
        Canvas canvas = null;

        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if (canvas != null) {
                        cannonView.updatePositions(1.0f / 60.0f);
                        cannonView.drawGameElements(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}