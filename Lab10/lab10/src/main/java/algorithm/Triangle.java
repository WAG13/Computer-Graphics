package algorithm;

import java.util.*;

public class Triangle {

    Vector a, b, c;

    Triangle(Vector a, Vector b, Vector c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vector getA() {
        return a;
    }

    public Vector getB() {
        return b;
    }

    public Vector getC() {
        return c;
    }

    boolean contains(Vector point) {
        double pab = point.subtract(a).getCrossProduct(b.subtract(a));
        double pbc = point.subtract(b).getCrossProduct(c.subtract(b));

        if (Math.signum(pab) != Math.signum(pbc)) {
            return false;
        }

        double pca = point.subtract(c).getCrossProduct(a.subtract(c));

        return Math.signum(pab) == Math.signum(pca);
    }

    boolean isPointInCircumscribedCircle(Vector point) {
        double a11 = a.x - point.x;
        double a21 = b.x - point.x;
        double a31 = c.x - point.x;

        double a12 = a.y - point.y;
        double a22 = b.y - point.y;
        double a32 = c.y - point.y;

        double a13 = (a.x - point.x) * (a.x - point.x) + (a.y - point.y) * (a.y - point.y);
        double a23 = (b.x - point.x) * (b.x - point.x) + (b.y - point.y) * (b.y - point.y);
        double a33 = (c.x - point.x) * (c.x - point.x) + (c.y - point.y) * (c.y - point.y);

        double det = a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31 - a12 * a21 * a33
                - a11 * a23 * a32;

        return Vector.isClockwiseTriplet(a, b, c) ? det > 0.0d : det < 0.0d;

    }

    boolean isAdjacentEdge(Edge edge) {
        return (a == edge.a || b == edge.a || c == edge.a) && (a == edge.b || b == edge.b || c == edge.b);
    }

    Vector getOppositeVertexFromEdge(Edge edge) {
        if (a != edge.a && a != edge.b) {
            return a;
        } else if (b != edge.a && b != edge.b) {
            return b;
        } else if (c != edge.a && c != edge.b) {
            return c;
        }

        return null;
    }

    boolean hasVertex(Vector vertex) {
        return a == vertex || b == vertex || c == vertex;

    }

    public Map.Entry<Double, Edge> findNearestEdge(Vector point) {
        TreeMap<Double, Edge> distances = new TreeMap<>();

        distances.put(computeClosestPoint(new Edge(a, b), point).subtract(point).getLength(), new Edge(a, b));
        distances.put(computeClosestPoint(new Edge(b, c), point).subtract(point).getLength(), new Edge(b, c));
        distances.put(computeClosestPoint(new Edge(c, a), point).subtract(point).getLength(), new Edge(c, a));

        return distances.firstEntry();
    }


    private Vector computeClosestPoint(Edge edge, Vector point) {
        Vector ab = edge.b.subtract(edge.a);
        double t = point.subtract(edge.a).getDotProduct(ab) / ab.getDotProduct(ab);

        if (t < 0.0d) {
            t = 0.0d;
        } else if (t > 1.0d) {
            t = 1.0d;
        }

        return edge.a.add(ab.multiply(t));
    }

    @Override
    public String toString() {
        return "Triangle[" + a + ", " + b + ", " + c + "]";
    }

}