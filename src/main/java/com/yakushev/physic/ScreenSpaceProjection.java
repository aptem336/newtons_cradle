package com.yakushev.physic;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ScreenSpaceProjection {
    public static Vector3D project(GLAutoDrawable drawable, Vector3D spaceVector) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        FloatBuffer modelView = FloatBuffer.allocate(16);
        FloatBuffer projection = FloatBuffer.allocate(16);
        IntBuffer viewport = IntBuffer.allocate(4);

        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelView);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

        FloatBuffer screenBuffer = FloatBuffer.allocate(3);
        glu.gluProject((float) spaceVector.getX(), (float) spaceVector.getY(), (float) spaceVector.getZ(), modelView, projection, viewport, screenBuffer);

        return new Vector3D(screenBuffer.get(0), viewport.get(2) - screenBuffer.get(1), screenBuffer.get(2));
    }

    public static Vector3D unProject(GLAutoDrawable drawable, Vector3D screenVector) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        FloatBuffer modelView = FloatBuffer.allocate(16);
        FloatBuffer projection = FloatBuffer.allocate(16);
        IntBuffer viewport = IntBuffer.allocate(4);

        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelView);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection);
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport);

        FloatBuffer screenZBuffer = FloatBuffer.allocate(1);
        gl.glReadPixels((int) screenVector.getX(), (int) (viewport.get(3) - screenVector.getY()), 1, 1, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, screenZBuffer);

        screenVector.set(screenVector.getX(), viewport.get(3) - screenVector.getY(), screenZBuffer.get(0));

        FloatBuffer spaceFloatBuffer = FloatBuffer.allocate(3);
        glu.gluUnProject((float) screenVector.getX(), (float) screenVector.getY(), (float) screenVector.getZ(), modelView, projection, viewport, spaceFloatBuffer);

        return new Vector3D(spaceFloatBuffer.get(0), spaceFloatBuffer.get(1), spaceFloatBuffer.get(2));
    }
}
