package com.example.cannongame;

public class Target extends GameElement {
    private int hitReward;

    public Target(CannonView view, int color, int soundId, float x, float y, float width, float height, float velocityY, int hitReward) {
        super(view, color, soundId, x, y, width, height, 0, velocityY);
        this.hitReward = hitReward;
    }

    @Override
    public void update(double interval) {
        super.update(interval);

        if (location.top < 0 || location.bottom > view.getScreenHeight()) {
            velocityY *= -1;
        }
    }

    public int getHitReward() {
        return hitReward;
    }
}