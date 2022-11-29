package com.yakushev.physic;

public interface Gravitated {
    Vector3D getVelocity();

    default void gravitate() {
        getVelocity().add(Vector3D.product(PhysicConstants.G, PhysicConstants.TIME_INTEGRATOR));
    }
}
