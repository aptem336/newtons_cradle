package com.yakushev.physic;

public class SuspensionConstraint implements Constraint {
    private final Vector3D velocity;
    private final Vector3D suspensionLocation;
    private final Vector3D location;
    private final double constraintSquareLen;
    private final double constraintLen;


    public SuspensionConstraint(Vector3D velocity, Vector3D suspensionLocation, Vector3D location) {
        this.velocity = velocity;
        this.suspensionLocation = suspensionLocation;
        this.location = location;
        this.constraintSquareLen = Vector3D.sum(suspensionLocation, location.getInvert()).squareLen();
        this.constraintLen = Math.sqrt(constraintSquareLen);
    }

    @Override
    public void solve() {
        Vector3D locationToSuspension = Vector3D.sum(suspensionLocation, location.getInvert());
        double locationToSuspensionSquareLen = locationToSuspension.squareLen();
        if (locationToSuspensionSquareLen > constraintSquareLen) {
            Vector3D locationToSuspensionNormal = locationToSuspension.getNormalized();
            double locationToSuspensionLen = Math.sqrt(locationToSuspensionSquareLen);
            location.add(Vector3D.product(locationToSuspensionNormal, locationToSuspensionLen - constraintLen));
            double velocityRelNormal = Vector3D.dotProduct(velocity, locationToSuspensionNormal);
            if (velocityRelNormal < 0.0) {
                velocity.add(Vector3D.product(locationToSuspensionNormal, -velocityRelNormal));
            }
        }
    }
}
