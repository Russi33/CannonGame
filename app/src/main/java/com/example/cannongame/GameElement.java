package com.example.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class GameElement {
    protected CannonView view;
    protected Paint paint = new Paint();
    protected RectF location;
    protected float velocityX, velocityY;
    protected float width, height;
    protected boolean isPlaying;

    public GameElement(CannonView view, int color, int soundId, float x, float y, float width, float height, float velocityX, float velocityY) {
        this.view = view;
        paint.setColor(color);
        this.location = new RectF(x, y, x + width, y + height);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.width = width;
        this.height = height;
        isPlaying = true;
    }

    // Atualiza a posição do elemento com base na velocidade
    public void update(double interval) {
        location.offset(velocityX * (float) interval, velocityY * (float) interval);
    }

    // Desenha o elemento na tela (Canvas)
    public void draw(Canvas canvas) {
        canvas.drawRect(location, paint);
    }

    // Verifica se colidiu com outro elemento
    public boolean collides(GameElement element) {
        return RectF.intersects(location, element.location);
    }

    public RectF getLocation() {
        return location;
    }
}