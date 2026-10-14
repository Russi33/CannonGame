package com.example.cannongame;

import android.graphics.Canvas;

public class Target extends GameElement {
    private int hitReward; // Pontos ganhos ao atingir o alvo

    public Target(CannonView view, int color, int soundId, float x, float y, float width, float height, float velocityY, int hitReward) {
        super(view, color, soundId, x, y, width, height, 0, velocityY);
        this.hitReward = hitReward;
    }

    // Sobrescreve o update para inverter a direção quando bater nas bordas superior/inferior
    @Override
    public void update(double interval) {
        super.update(interval);

        // Se bater na borda superior ou inferior da tela, inverte a velocidade (velocityY)
        if (location.top < 0 || location.bottom > view.getScreenHeight()) {
            velocityY *= -1;
        }
    }

    public int getHitReward() {
        return hitReward;
    }
}