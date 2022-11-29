package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.gl2.GLUT;
import com.yakushev.Displayable;
import com.yakushev.physic.Gravitated;
import com.yakushev.physic.Integrated;
import com.yakushev.physic.Vector3D;

public class Ball implements Displayable, TimeVarying, Integrated, Gravitated {
    private final double r;
    private final Vector3D location;
    private final Vector3D velocity;

    public Ball(double r, Vector3D location) {
        this.r = r;
        this.location = location;
        this.velocity = new Vector3D();
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glPushMatrix();
        gl.glTranslated(location.getX(), location.getY(), location.getZ());
        glut.glutSolidSphere(r, 40, 40);
        gl.glPopMatrix();
    }

    @Override
    public void vary() {
        integrate();
        gravitate();
    }

    @Override
    public Vector3D getLocation() {
        return location;
    }

    @Override
    public Vector3D getVelocity() {
        return velocity;
    }
}
