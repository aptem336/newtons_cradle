package com.yakushev.physic;

public class Vector3D {
    private double x, y, z;

    public Vector3D() {
        x = y = z = 0;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static Vector3D sum(Vector3D v1, Vector3D v2) {
        return new Vector3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public static Vector3D product(Vector3D v, double value) {
        return new Vector3D(v.x * value, v.y * value, v.z * value);
    }

    public void add(Vector3D v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void mul(double multiplier) {
        x *= multiplier;
        y *= multiplier;
        z *= multiplier;
    }

    public double squareLen() {
        return x * x + y * y + z * z;
    }

    public double len() {
        return Math.sqrt(squareLen());
    }

    public Vector3D getInvert() {
        return new Vector3D(-x, -y, -z);
    }

    public Vector3D getNormalized() {
        double len = len();
        return new Vector3D(x / len, y / len, z / len);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}