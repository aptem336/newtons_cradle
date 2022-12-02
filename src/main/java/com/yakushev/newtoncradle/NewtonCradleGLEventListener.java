package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.yakushev.physic.ScreenSpaceProjection;
import com.yakushev.physic.SuspensionConstraint;
import com.yakushev.physic.Vector3D;

import java.awt.event.*;

public class NewtonCradleGLEventListener implements GLEventListener, KeyListener, MouseWheelListener, MouseMotionListener, MouseListener {
    private static final int DEFAULT_BALLS_COUNT = 5;
    private static final double DEFAULT_BALLS_R = 1;
    private static final double DEFAULT_FIBER_LEN = 10;
    private static final int MIN_BALLS_COUNT = 2;
    private static final int MAX_BALLS_COUNT = 15;
    private int ballsCount = DEFAULT_BALLS_COUNT;
    private double ballsR = DEFAULT_BALLS_R;
    private double fiberLen = DEFAULT_FIBER_LEN;

    private NewtonGradle newtonGradle;

    private boolean mousePressed = false;
    private Vector3D mouseLocation;
    private Vector3D mouseLocationUnProject;
    private SuspensionConstraint mouseSuspensionConstraint;
    private Ball ball;

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
        if (mousePressed) {
            double spaceLenToScreenCoefficient = Vector3D.sum(ScreenSpaceProjection.project(glAutoDrawable, new Vector3D(0.0, 0.0, 0.0)),
                    ScreenSpaceProjection.project(glAutoDrawable, new Vector3D(1.0, 0.0, 0.0)).getInvert()).len();
            if (mouseSuspensionConstraint == null) {
                newtonGradle.getBalls().stream().filter(ball -> {
                    Vector3D ballLocationProject = ScreenSpaceProjection.project(glAutoDrawable, ball.getLocation());
                    Vector3D ballProjectLocationToMouse = Vector3D.sum(mouseLocation, ballLocationProject.getInvert());
                    return ballProjectLocationToMouse.squareLen() < ballsR * ballsR * spaceLenToScreenCoefficient * spaceLenToScreenCoefficient;
                }).findFirst().ifPresent(ball -> {
                    this.ball = ball;
                    Vector3D ballLocationProject = ScreenSpaceProjection.project(glAutoDrawable, ball.getLocation());
                    Vector3D ballProjectLocationToMouse = Vector3D.sum(mouseLocation, ballLocationProject.getInvert());
                    ballProjectLocationToMouse.set(ballProjectLocationToMouse.getX(), -ballProjectLocationToMouse.getY(), 0.0);
                    mouseLocationUnProject = Vector3D.sum(ball.getLocation(), Vector3D.product(ballProjectLocationToMouse, 1.0 / spaceLenToScreenCoefficient));
                    mouseSuspensionConstraint = new SuspensionConstraint(ball.getVelocity(), mouseLocationUnProject, ball.getLocation(), 0.0);
                    mouseSuspensionConstraint.solve();
                });
            } else {
                Vector3D ballLocationProject = ScreenSpaceProjection.project(glAutoDrawable, ball.getLocation());
                Vector3D ballProjectLocationToMouse = Vector3D.sum(mouseLocation, ballLocationProject.getInvert());
                ballProjectLocationToMouse.set(ballProjectLocationToMouse.getX(), -ballProjectLocationToMouse.getY(), 0.0);
                Vector3D newMouseLocationUnProject = Vector3D.sum(ball.getLocation(), Vector3D.product(ballProjectLocationToMouse, 1.0 / spaceLenToScreenCoefficient));
                mouseLocationUnProject.set(newMouseLocationUnProject.getX(), newMouseLocationUnProject.getY(), newMouseLocationUnProject.getZ());
                mouseSuspensionConstraint.solve();
            }
        } else {
            mouseSuspensionConstraint = null;
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseLocation = new Vector3D(e.getX(), e.getY(), 0.0);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLocation = new Vector3D(e.getX(), e.getY(), 0.0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseLocation = new Vector3D(e.getX(), e.getY(), 0.0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
