package com.example.cannongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;

public class CannonView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int BALL_SPEED = 800;
    public static final int BALL_RADIUS = 15;
    public static final int TARGET_PIECES = 7;
    public static final int MISS_PENALTY = 2;
    public static final int HIT_REWARD = 3;

    private Cannon cannon;
    private Cannonball cannonball;
    private Blocker blocker;
    private List<Target> targets;

    private CannonThread cannonThread;
    private SurfaceHolder holder;
    private Paint textPaint;
    private Paint backgroundPaint;

    private int screenWidth;
    private int screenHeight;
    private double timeLeft;
    private int shotsFired;
    private double totalElapsedTime;
    private boolean gameOver;

    public CannonView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        textPaint = new Paint();
        textPaint.setTextSize(40);
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
    }

    public void newGame() {
        if (screenWidth == 0 || screenHeight == 0) return;

        int cannonBaseRadius = screenHeight / 18;
        int cannonBarrelLength = screenWidth / 8;
        int cannonBarrelWidth = screenHeight / 20;

        cannon = new Cannon(this, cannonBaseRadius, cannonBarrelLength, cannonBarrelWidth, screenWidth, screenHeight);

        int blockerWidth = screenWidth / 36;
        int blockerHeight = screenHeight / 8;
        int blockerX = (screenWidth * 5) / 8;
        int blockerY = (screenHeight - blockerHeight) / 2;
        int blockerSpeed = screenHeight / 2;
        blocker = new Blocker(this, Color.BLACK, 0, (float) blockerX, (float) blockerY, (float) blockerWidth, (float) blockerHeight, (float) blockerSpeed, blockerX);

        targets = new ArrayList<>();
        int targetWidth = screenWidth / 36;
        int targetHeight = screenHeight / 8;
        int targetX = (screenWidth * 7) / 8;
        int targetY = (screenHeight - targetHeight) / 2;
        int targetSpeed = screenHeight / 3;

        for (int i = 0; i < TARGET_PIECES; i++) {
            targets.add(new Target(this, Color.BLUE, 0, (float) targetX, (float) targetY, (float) targetWidth, (float) targetHeight, (float) targetSpeed, targetX));
        }

        cannonball = null;
        timeLeft = 10.0;
        shotsFired = 0;
        totalElapsedTime = 0.0;
        gameOver = false;

        if (cannonThread == null || !cannonThread.isAlive()) {
            cannonThread = new CannonThread(getHolder(), this);
            cannonThread.setRunning(true);
            cannonThread.start();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cannonThread = new CannonThread(holder, this);
        cannonThread.setRunning(true);
        cannonThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        newGame();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        if (cannonThread != null) {
            cannonThread.setRunning(false);
            while (retry) {
                try {
                    cannonThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            // Se o jogo acabou, qualquer toque na tela inicia uma nova partida imediatamente
            if (gameOver) {
                newGame();
                return true;
            }
            alignAndFireCannonball(event);
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (!gameOver) {
                alignAndFireCannonball(event);
            }
        }

        return true;
    }

    public void alignAndFireCannonball(MotionEvent event) {
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());

        if (cannon != null) {
            double centerVectorY = (screenHeight / 2.0) - touchPoint.y;
            double centerVectorX = touchPoint.x;
            double angle = Math.atan2(centerVectorY, centerVectorX);

            cannon.align(angle);

            if ((cannonball == null || !cannonball.isOnScreen()) && !gameOver) {
                cannonball = cannon.fire();
                shotsFired++;
            }
        }
    }

    public void updatePositions(double elapsedTime) {
        if (gameOver) return;

        double interval = elapsedTime;
        timeLeft -= interval;
        totalElapsedTime += interval;

        if (cannonball != null && cannonball.isOnScreen()) {
            cannonball.update(interval);
        }
        if (blocker != null) {
            blocker.update(interval);
        }
        for (Target target : targets) {
            target.update(interval);
        }

        testForCollisions();

        if (timeLeft <= 0) {
            timeLeft = 0;
            gameOver = true;
        }

        if (targets.isEmpty()) {
            gameOver = true;
        }
    }

    public void testForCollisions() {
        if (cannonball != null && cannonball.isOnScreen()) {
            if (blocker != null && cannonball.collidesWith(blocker)) {
                cannonball.reverseVelocityX();
                timeLeft -= MISS_PENALTY;
            }

            for (int i = targets.size() - 1; i >= 0; i--) {
                Target target = targets.get(i);
                if (cannonball.collidesWith(target)) {
                    targets.remove(i);
                    cannonball.removeCannonball();
                    timeLeft += HIT_REWARD;
                    break;
                }
            }
        }
    }

    public void drawGameElements(Canvas canvas) {
        if (canvas == null) return;

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

        canvas.drawText("Tempo restante: " + String.format("%.1f", timeLeft) + "s", 30, 50, textPaint);
        canvas.drawText("Tiros disparados: " + shotsFired, 30, 100, textPaint);

        if (cannon != null) cannon.draw(canvas);
        if (cannonball != null && cannonball.isOnScreen()) cannonball.draw(canvas);
        if (blocker != null) blocker.draw(canvas);

        for (Target target : targets) {
            target.draw(canvas);
        }

        if (gameOver) {
            String message = targets.isEmpty() ? "VOCÊ VENCEU!" : "GAME OVER!";
            canvas.drawText(message, screenWidth / 3.0f, screenHeight / 2.0f, textPaint);
            canvas.drawText("Toque na tela para jogar novamente", screenWidth / 3.0f, screenHeight / 2.0f + 60, textPaint);
        }
    }

    public void pause() {
        if (cannonThread != null) {
            cannonThread.setRunning(false);
            boolean retry = true;
            while (retry) {
                try {
                    cannonThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void resume() {
        if (cannonThread == null || !cannonThread.isAlive()) {
            cannonThread = new CannonThread(getHolder(), this);
            cannonThread.setRunning(true);
            cannonThread.start();
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Cannonball getCannonball() {
        return cannonball;
    }
}