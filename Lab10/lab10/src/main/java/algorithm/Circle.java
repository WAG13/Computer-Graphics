package algorithm;

public class Circle {

    private Vector center;
    private double radius;

    public Circle(Vector center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vector getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public static Circle fromTriangle(Triangle triangle) {
        Vector a = triangle.a, b = triangle.b, c = triangle.c;

        double offset = Math.pow(b.x, 2) + Math.pow(b.y, 2);
        double bc = (Math.pow(a.x, 2) + Math.pow(a.y, 2) - offset) / 2.0;
        double cd = (offset - Math.pow(c.x, 2) - Math.pow(c.y, 2)) / 2.0;
        double det = (a.x - b.x) * (b.y - c.y) - (b.x - c.x) * (a.y - b.y);

        double idet = 1 / det;

        double centerx = (bc * (b.y - c.y) - cd * (a.y - b.y)) * idet;
        double centery = (cd * (a.x - b.x) - bc * (b.x - c.x)) * idet;
        double radius = Math.sqrt(Math.pow(b.x - centerx, 2) + Math.pow(b.y - centery, 2));

        return new Circle(new Vector(centerx, centery), radius);
    }
}
