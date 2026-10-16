package com.example.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Cannon {
    private CannonView view;
    private int baseRadius;
    private int barrelLength;
    private Point barrelEnd = new Point();
    private double aimAngle;
    private int viewWidth, viewHeight;
    private Paint paint = new Paint();

    public Cannon(CannonView view, int baseRadius, int barrelLength, int barrelWidth, int viewWidth, int viewHeight) {
        this.view = view;
        this.baseRadius = baseRadius;
        this.barrelLength = barrelLength;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        paint.setStrokeWidth(barrelWidth);
        paint.setColor(android.graphics.Color.BLACK);
        align(Math.PI / 2);
    }

    public void align(double angle) {
        aimAngle = angle;
        barrelEnd.x = (int) (barrelLength * Math.cos(aimAngle));
        barrelEnd.y = (int) (viewHeight / 2.0 - barrelLength * Math.sin(aimAngle));
    }

    public Cannonball fire() {
        int velocityX = (int) (CannonView.BALL_SPEED * Math.cos(aimAngle));
        int velocityY = (int) (-CannonView.BALL_SPEED * Math.sin(aimAngle));

        return new Cannonball(view, android.graphics.Color.BLACK, 0, barrelEnd.x, barrelEnd.y,
                CannonView.BALL_RADIUS, velocityX, velocityY);
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(0, viewHeight / 2.0f, barrelEnd.x, barrelEnd.y, paint);
        canvas.drawCircle(0, viewHeight / 2.0f, baseRadius, paint);
    }

    public Point getBarrelEnd() {
        return barrelEnd;
    }
}