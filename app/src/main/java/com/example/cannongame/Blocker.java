package com.example.cannongame;

public class Blocker extends GameElement {
    private int distance; // Distância do canhão

    public Blocker(CannonView view, int color, int soundId, float x, float y, float width, float height, float velocityY, int distance) {
        super(view, color, soundId, x, y, width, height, 0, velocityY);
        this.distance = distance;
    }

    // Sobrescreve o update para inverter a direção nas bordas superior/inferior
    @Override
    public void update(double interval) {
        super.update(interval);

        if (location.top < 0 || location.bottom > view.getScreenHeight()) {
            velocityY *= -1;
        }
    }

    public int getDistance() {
        return distance;
    }
}
