package com.yakushev.newtoncradle;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.yakushev.Displayable;
import com.yakushev.physic.*;

import java.util.ArrayList;
import java.util.List;

public class NewtonGradle implements Displayable, TimeVarying {
    private static final int DEFAULT_BALLS_COUNT = 5;
    private static final double DEFAULT_BALLS_R = 1;
    private static final double DEFAULT_FIBER_LEN = 10;
    private static final int SUSPENSION_CONSTRAINT_SOLVE_ITERATION_COUNT = 10;
    private static final int COLLIDE_VELOCITY_SOLVE_ITERATION_COUNT = 500;
    private final int ballsCount;
    private final double ballsR;
    private final double ballsSquareDoubleR;
    private final double fiberLen;
    private final List<Ball> balls;
    private final List<Fiber> fibers;
    private final List<SuspensionConstraint> suspensionConstraints;
    private final Rack rack;

    public NewtonGradle() {
        this(DEFAULT_BALLS_COUNT, DEFAULT_BALLS_R, DEFAULT_FIBER_LEN);
    }

    public NewtonGradle(int ballsCount, double ballsR, double fiberLen) {
        this.ballsCount = ballsCount;
        this.ballsR = ballsR;
        this.ballsSquareDoubleR = (ballsR + ballsR) * (ballsR + ballsR);
        this.fiberLen = fiberLen;
        balls = new ArrayList<>();
        fibers = new ArrayList<>();
        suspensionConstraints = new ArrayList<>();
        Vector3D suspensionLocationFar = new Vector3D(0.0, fiberLen * Math.sin(Math.PI / 3), fiberLen * Math.cos(Math.PI / 3));
        Vector3D suspensionLocationNear = new Vector3D(0.0, fiberLen * Math.sin(Math.PI / 3), -fiberLen * Math.cos(Math.PI / 3));
        Vector3D suspensionConstraintInitialRelax = new Vector3D(0.0, fiberLen / 4, 0.0);

        for (int i = 0; i < ballsCount; i++) {
            Vector3D ballLocation = new Vector3D(i * ballsR * 2.0, 0.0, 0.0);
            Ball ball = new Ball(ballsR, ballLocation);
            balls.add(ball);
            Vector3D ballSuspensionLocationFar = Vector3D.sum(ballLocation, suspensionLocationFar);
            fibers.add(new Fiber(ballLocation, ballSuspensionLocationFar));
            suspensionConstraints.add(new SuspensionConstraint(ball.getVelocity(), ballSuspensionLocationFar, ballLocation));

            Vector3D ballSuspensionLocationNear = Vector3D.sum(ballLocation, suspensionLocationNear);
            fibers.add(new Fiber(ballLocation, ballSuspensionLocationNear));
            suspensionConstraints.add(new SuspensionConstraint(ball.getVelocity(), ballSuspensionLocationNear, ballLocation));
            
            ball.getLocation().add(suspensionConstraintInitialRelax);
        }
        this.rack = new Rack(ballsCount, ballsR, fiberLen);
        balls.get(0).getVelocity().add(new Vector3D(-0.5 / PhysicConstants.TIME_INTEGRATOR, 0.0, 0.0));
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glPushMatrix();
        gl.glTranslated(-(ballsCount - 1) * ballsR, -fiberLen / 2.0 + ballsR, 0.0);
        balls.forEach(ball -> ball.display(glAutoDrawable));
        fibers.forEach(fiber -> fiber.display(glAutoDrawable));
        rack.display(glAutoDrawable);
        gl.glPopMatrix();
    }

    @Override
    public void vary() {
        List<CollideVelocityConstraint> collideVelocityConstraints = new ArrayList<>();
        balls.forEach(TimeVarying::vary);
        for (int i = 0; i < balls.size(); i++) {
            Ball ballI = balls.get(i);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ballJ = balls.get(j);
                Vector3D ballIToJVector = Vector3D.sum(ballI.getLocation(), ballJ.getLocation().getInvert());
                double ballIToJSquareLen = ballIToJVector.squareLen();
                if (ballIToJSquareLen <= ballsSquareDoubleR) {
                    Vector3D ballIToJVectorNormal = ballIToJVector.getNormalized();
                    collideVelocityConstraints.add(new CollideVelocityConstraint(ballI.getVelocity(),
                            ballJ.getVelocity(),
                            ballIToJVectorNormal));
                }
            }
        }
        for (int i = 0; i < SUSPENSION_CONSTRAINT_SOLVE_ITERATION_COUNT; i++) {
            suspensionConstraints.forEach(Constraint::solve);
        }
        for (int i = 0; i < COLLIDE_VELOCITY_SOLVE_ITERATION_COUNT; i++) {
            collideVelocityConstraints.forEach(Constraint::solve);
        }
    }
}
