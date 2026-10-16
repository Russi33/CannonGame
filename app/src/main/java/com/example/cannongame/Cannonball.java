package com.example.cannongame;

import android.graphics.Canvas;

public class Cannonball extends GameElement {
    private float radius;
    private boolean onScreen;

    public Cannonball(CannonView view, int color, int soundId, float x, float y, float radius, float velocityX, float velocityY) {
        super(view, color, soundId, x - radius, y - radius, 2 * radius, 2 * radius, velocityX, velocityY);
        this.radius = radius;
        onScreen = true;
    }

    @Override
    public void update(double interval) {
        super.update(interval);

        if (location.left > view.getScreenWidth() ||
                location.right < 0 ||
                location.bottom < 0 ||
                location.top > view.getScreenHeight()) {
            onScreen = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(location.left + radius, location.top + radius, radius, paint);
    }

    public void reverseVelocityX() {
        velocityX *= -1;
    }

    public void removeCannonball() {
        onScreen = false;
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}