package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.yakushev.Displayable;
import com.yakushev.physic.Vector3D;

public class Fiber implements Displayable {
    private final Vector3D a;
    private final Vector3D b;

    public Fiber(Vector3D a, Vector3D b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glBegin(GL2.GL_LINES);
        gl.glNormal3d(0.0, 0.0, 1.0);
        gl.glVertex3d(a.getX(), a.getY(), a.getZ());
        gl.glVertex3d(b.getX(), b.getY(), b.getZ());
        gl.glEnd();
    }
}
