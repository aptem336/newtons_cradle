package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class NewtonCradleGLEventListener implements GLEventListener, KeyListener, MouseWheelListener {
    private static final int DEFAULT_BALLS_COUNT = 5;
    private static final double DEFAULT_BALLS_R = 1;
    private static final double DEFAULT_FIBER_LEN = 10;
    private static final int MIN_BALLS_COUNT = 2;
    private static final int MAX_BALLS_COUNT = 15;
    private int ballsCount = DEFAULT_BALLS_COUNT;
    private double ballsR = DEFAULT_BALLS_R;
    private double fiberLen = DEFAULT_FIBER_LEN;

    private NewtonGradle newtonGradle;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        newtonGradle = new NewtonGradle(ballsCount, ballsR, fiberLen);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        newtonGradle.vary();
        gl.glTranslated(-(ballsCount - 1) * ballsR, -fiberLen / 2.0 + ballsR, 0.0);
        newtonGradle.display(glAutoDrawable);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            ballsCount = DEFAULT_BALLS_COUNT;
            ballsR = DEFAULT_BALLS_R;
            fiberLen = DEFAULT_FIBER_LEN;
            newtonGradle = new NewtonGradle(ballsCount, ballsR, fiberLen);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        ballsCount = Math.max(MIN_BALLS_COUNT, ballsCount - e.getWheelRotation());
        ballsCount = Math.min(MAX_BALLS_COUNT, ballsCount);
        newtonGradle = new NewtonGradle(ballsCount, ballsR, fiberLen);
    }
}
