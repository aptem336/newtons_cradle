package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;
import com.yakushev.Displayable;

public class Rack implements Displayable {
    private final int ballsCount;
    private final double ballsR;
    private final double fiberLen;

    public Rack(int ballsCount, double ballsR, double fiberLen) {
        this.ballsCount = ballsCount;
        this.ballsR = ballsR;
        this.fiberLen = fiberLen;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glTranslated(-ballsR * 2, fiberLen * Math.sin(Math.PI / 3), fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        glut.glutSolidCylinder(0.125, (ballsCount + 1) * ballsR * 2, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-ballsR * 2, fiberLen * Math.sin(Math.PI / 3), fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        gl.glRotated(90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.125, fiberLen + ballsR, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(ballsCount * ballsR * 2, fiberLen * Math.sin(Math.PI / 3), fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        gl.glRotated(90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.125, fiberLen + ballsR, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-ballsR * 2, fiberLen * Math.sin(Math.PI / 3), -fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        glut.glutSolidCylinder(0.125, (ballsCount + 1) * ballsR * 2, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-ballsR * 2, fiberLen * Math.sin(Math.PI / 3), -fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        gl.glRotated(90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.125, fiberLen + ballsR, 40, 40);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(ballsCount * ballsR * 2, fiberLen * Math.sin(Math.PI / 3), -fiberLen * Math.cos(Math.PI / 3));
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        gl.glRotated(90.0, 1.0, 0.0, 0.0);
        glut.glutSolidCylinder(0.125, fiberLen + ballsR, 40, 40);
        gl.glPopMatrix();
    }
}
