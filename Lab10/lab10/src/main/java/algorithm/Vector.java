package algorithm;

public class Vector {

    double x;
    double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Vector subtract(Vector vector) {
        return new Vector(x - vector.x, y - vector.y);
    }

    Vector add(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    Vector multiply(double v) {
        return new Vector(x * v, y * v);
    }

    double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    double getDotProduct(Vector vector) {
        return x * vector.x + y * vector.y;
    }

    double getCrossProduct(Vector vector) {
        return y * vector.x - x * vector.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static boolean isClockwiseTriplet(Vector a, Vector b, Vector c) {
        double a11 = a.x - c.x;
        double a21 = b.x - c.x;

        double a12 = a.y - c.y;
        double a22 = b.y - c.y;

        double det = a11 * a22 - a12 * a21;

        return det > 0.0d;
    }
}