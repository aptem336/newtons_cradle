package com.yakushev.physic;

public interface Integrated {
    Vector3D getLocation();

    Vector3D getVelocity();

    default void integrate() {
        getLocation().add(Vector3D.product(getVelocity(), PhysicConstants.TIME_INTEGRATOR));
    }
}
