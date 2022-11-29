package com.yakushev.physic;

public interface PhysicConstants {
    double TIME_INTEGRATOR = 1.0 / 100.0;
    Vector3D G = new Vector3D(0.0, -9.8 / TIME_INTEGRATOR, 0.0);
}
