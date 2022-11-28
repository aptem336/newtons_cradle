package com.yakushev;

import com.jogamp.opengl.GLEventListener;

import java.awt.*;
import java.util.ArrayList;

public class GLSettings {
    private static final java.util.List<GLEventListener> GL_EVENT_LISTENERS = new ArrayList<GLEventListener>() {{
        add(new Base3GLEventListener());
    }};
    private static final Dimension MAIN_FRAME_SIZE = new Dimension(1000, 1000);
    private static final Color3f CLEAR_COLOR = new Color3f(0.1F, 0.1F, 0.1F);

    public static java.util.List<GLEventListener> getGlEventListeners() {
        return GL_EVENT_LISTENERS;
    }

    public static Dimension getMainFrameSize() {
        return MAIN_FRAME_SIZE;
    }

    public static Color3f getClearColor() {
        return CLEAR_COLOR;
    }
}
