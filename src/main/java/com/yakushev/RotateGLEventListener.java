package com.yakushev;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RotateGLEventListener implements GLEventListener, KeyListener {
    private static final double ANGLE_INCREMENT = 5;
    private static final double R_INCREMENT = 5;
    private double polarAngle = 0;
    private double azimuthalAngle = 0;
    private double r = 50;
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glTranslated(0.0, 0.0, -Math.abs(r));
        gl.glRotated(polarAngle, 1.0, 0.0, 0.0);
        gl.glRotated(azimuthalAngle, 0.0, 1.0, 0.0);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            polarAngle -= ANGLE_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            polarAngle += ANGLE_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            azimuthalAngle -= ANGLE_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            azimuthalAngle += ANGLE_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            r += R_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            r -= R_INCREMENT;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            r = 50;
            polarAngle = 0;
            azimuthalAngle = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
