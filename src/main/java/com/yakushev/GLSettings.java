package com.yakushev;

import com.jogamp.opengl.GLEventListener;
import com.yakushev.newtoncradle.NewtonCradleGLEventListener;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GLSettings {
    private static final RotateGLEventListener CAMERA_GL_EVENT_LISTENER = new RotateGLEventListener();
    private static final NewtonCradleGLEventListener NEWTON_CRADLE_GL_EVENT_LISTENER = new NewtonCradleGLEventListener();
    private static final java.util.List<GLEventListener> GL_EVENT_LISTENERS = new ArrayList<GLEventListener>() {{
        add(new Base3GLEventListener());
        add(CAMERA_GL_EVENT_LISTENER);
        add(new StaticLightGLEventListener(new int[]{0, 0, 1, 0}));
        add(NEWTON_CRADLE_GL_EVENT_LISTENER);
    }};
    private static final java.util.List<KeyListener> KEY_LISTENERS = new ArrayList<KeyListener>() {{
        add(CAMERA_GL_EVENT_LISTENER);
    }};
    private static final Dimension MAIN_FRAME_SIZE = new Dimension(1000, 1000);
    private static final Color3f CLEAR_COLOR = new Color3f(0.1F, 0.1F, 0.1F);

    public static java.util.List<GLEventListener> getGlEventListeners() {
        return GL_EVENT_LISTENERS;
    }

    public static List<KeyListener> getKeyListeners() {
        return KEY_LISTENERS;
    }

    public static Dimension getMainFrameSize() {
        return MAIN_FRAME_SIZE;
    }

    public static Color3f getClearColor() {
        return CLEAR_COLOR;
    }
}
