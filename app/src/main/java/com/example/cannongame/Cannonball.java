package com.example.cannongame;

import android.graphics.Canvas;
import android.graphics.RectF;

public class Cannonball extends GameElement {
    private float radius;
    private boolean onScreen;

    public Cannonball(CannonView view, int color, int soundId, float x, float y, float radius, float velocityX, float velocityY) {
        super(view, color, soundId, x, y, 2 * radius, 2 * radius, velocityX, velocityY);
        this.radius = radius;
        onScreen = true;
    }

    // Sobrescreve o método update para gerenciar o movimento e os limites da tela
    @Override
    public void update(double interval) {
        super.update(interval);

        // Verifica se a bala saiu dos limites da tela
        if (location.left < 0 || location.right > view.getScreenWidth() ||
                location.top < 0 || location.bottom > view.getScreenHeight() ||
                location.top > view.getScreenHeight()) {
            onScreen = false;
        }
    }

    // Desenha a bala como um círculo na tela
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(location.left + radius, location.top + radius, radius, paint);
    }

    public boolean isOnScreen() {
        return onScreen;
    }
}
