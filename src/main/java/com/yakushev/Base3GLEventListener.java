package com.yakushev;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Base3GLEventListener implements GLEventListener {

    /**
     * Функция инициализации
     *
     * @param glAutoDrawable контекст отрисовки
     */
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glEnable(GL2.GL_DEPTH_TEST);

        Color3f clearColor = GLSettings.getClearColor();
        gl.glClearColor((float) clearColor.getRed(),
                (float) clearColor.getGreen(),
                (float) clearColor.getBlue(),
                1.0f);
    }

    /**
     * @param glAutoDrawable контекст отрисовки
     */
    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    /**
     * Функция отрисовки кадра
     *
     * @param glAutoDrawable контекст отрисовки
     */
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        for (int i = 0; i < GLSettings.getMessages().size(); i++) {
            gl.glWindowPos2i(10, GLSettings.getMainFrameSize().height - 20 - 20 * i);
            glut.glutBitmapString(GLUT.BITMAP_9_BY_15, GLSettings.getMessages().get(i));
        }
    }

    /**
     * Функция изменения размеров окна
     *
     * @param glAutoDrawable контекст отрисовки
     * @param x        позиция окна x
     * @param y        позиция окна y
     * @param width    ширина окна
     * @param height   высота окна
     */
    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        //установка обзорной рамки
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        GLU glu = new GLU();
        //установка матрицы перспективы:
        //fovy - поле угла зрения в градусах в направлении y.
        //aspect - Пропорции, определяющие поле представления в направлении x. Пропорции — это соотношение x (ширина) к y (высота).
        //zNear - Расстояние от зрителя до близкого вырезки плоскости (всегда положительное).
        //zFar - Расстояние от зрителя до дальней плоскости вырезки (всегда положительно).
        double aspect = (double) width / (double) height;
        glu.gluPerspective(45.0, aspect, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
