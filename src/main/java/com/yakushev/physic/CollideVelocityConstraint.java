package com.yakushev.physic;

public class CollideVelocityConstraint implements Constraint {
    private final Vector3D velocityA;
    private final Vector3D velocityB;
    private final Vector3D collisionNormal;

    public CollideVelocityConstraint(Vector3D velocityA, Vector3D velocityB, Vector3D collisionNormal) {
        this.velocityA = velocityA;
        this.velocityB = velocityB;
        this.collisionNormal = collisionNormal;
    }

    @Override
    public void solve() {
        double summaryRelationVelocity = 0.0;
        summaryRelationVelocity += Vector3D.dotProduct(velocityA, collisionNormal);
        summaryRelationVelocity -= Vector3D.dotProduct(velocityB, collisionNormal);
        if (summaryRelationVelocity < 0.0) {
            Vector3D summaryRelationVelocityOriented = Vector3D.product(collisionNormal, summaryRelationVelocity);
            velocityA.add(summaryRelationVelocityOriented.getInvert());
            velocityB.add(summaryRelationVelocityOriented);
        }
    }
}
