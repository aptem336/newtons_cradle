package com.yakushev.newtoncradle;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

public class NewtonCradleGLEventListener implements GLEventListener {
    private NewtonGradle newtonGradle;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        newtonGradle = new NewtonGradle();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        newtonGradle.vary();
        newtonGradle.display(glAutoDrawable);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
    }
}
