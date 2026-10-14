package com.example.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Cannon {
    private int baseRadius; // Raio da base do canhão
    private int barrelLength; // Comprimento do cano do canhão
    private Point barrelEnd = new Point(); // Ponto final do cano
    private double aimAngle; // Ângulo de mira
    private int viewWidth, viewHeight; // Dimensões da tela
    private Paint paint = new Paint();
    private Cannonball cannonball; // A bala atual disparada

    public Cannon(CannonView view, int baseRadius, int barrelLength, int bulletSpeed, int x, int y) {
        this.baseRadius = baseRadius;
        this.barrelLength = barrelLength;
        this.viewWidth = x;
        this.viewHeight = y;
        paint.setStrokeWidth(barrelLength / 4); // Espessura do cano
        paint.setColor(android.graphics.Color.BLACK);
        align(Math.PI / 2); // Aponta para cima inicialmente
    }

    // Altera o ângulo de mira do canhão
    public void align(double angle) {
        aimAngle = angle;
        barrelEnd.x = (int) (barrelLength * Math.sin(aimAngle)) + viewWidth / 2;
        barrelEnd.y = (int) (-barrelLength * Math.cos(aimAngle)) + viewHeight;
    }

    // Dispara uma bala do canhão
    public Cannonball fire() {
        // Velocidade baseada no ângulo de mira
        int velocityX = (int) (CannonView.BALL_SPEED * Math.sin(aimAngle));
        int velocityY = (int) (-CannonView.BALL_SPEED * Math.cos(aimAngle));

        cannonball = new Cannonball(view, android.graphics.Color.RED, 0, barrelEnd.x, barrelEnd.y,
                CannonView.BALL_RADIUS, velocityX, velocityY);
        return cannonball;
    }

    // Desenha o canhão na tela
    public void draw(Canvas canvas) {
        // Desenha o cano do canhão
        canvas.drawLine(viewWidth / 2, viewHeight, barrelEnd.x, barrelEnd.y, paint);
        // Desenha a base circular do canhão
        canvas.drawCircle(viewWidth / 2, viewHeight, baseRadius, paint);
    }
}