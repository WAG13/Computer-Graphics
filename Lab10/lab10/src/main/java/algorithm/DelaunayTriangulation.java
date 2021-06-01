package algorithm;

import ui.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelaunayTriangulation {

    public static List<Triangle> calculate(List<Vector> points) {

        Triangles triangles = new Triangles();

        if (points == null || points.size() < 3) {
            return triangles.getTriangles();
        }

        List<Vector> pointSet = new ArrayList<>(points);
        Collections.shuffle(pointSet);

        Triangle superTriangle = getSuperTriangle();

        triangles.addAll(superTriangle);

        for (Vector point : pointSet) {
            Triangle triangle = triangles.findContainingTriangle(point);

            if (triangle == null) {
                Edge edge = triangles.findNearestEdge(point);

                Triangle first = triangles.findTriangleSharing(edge);
                Triangle second = triangles.findNeighbour(first, edge);

                Vector firstNoneEdgeVertex = first.getOppositeVertexFromEdge(edge);
                Vector secondNoneEdgeVertex = second.getOppositeVertexFromEdge(edge);

                triangles.remove(first);
                triangles.remove(second);

                Triangle triangle1 = new Triangle(edge.a, firstNoneEdgeVertex, point);
                Triangle triangle2 = new Triangle(edge.b, firstNoneEdgeVertex, point);
                Triangle triangle3 = new Triangle(edge.a, secondNoneEdgeVertex, point);
                Triangle triangle4 = new Triangle(edge.b, secondNoneEdgeVertex, point);

                triangles.addAll(triangle1, triangle2, triangle3, triangle4);

                updateRule(triangles, triangle1, new Edge(edge.a, firstNoneEdgeVertex), point);
                updateRule(triangles, triangle2, new Edge(edge.b, firstNoneEdgeVertex), point);
                updateRule(triangles, triangle3, new Edge(edge.a, secondNoneEdgeVertex), point);
                updateRule(triangles, triangle4, new Edge(edge.b, secondNoneEdgeVertex), point);
            } else {
                Vector a = triangle.a;
                Vector b = triangle.b;
                Vector c = triangle.c;

                triangles.remove(triangle);

                Triangle triangle1 = new Triangle(a, b, point);
                Triangle triangle2 = new Triangle(b, c, point);
                Triangle triangle3 = new Triangle(c, a, point);

                triangles.addAll(triangle1, triangle2, triangle3);

                updateRule(triangles, triangle1, new Edge(a, b), point);
                updateRule(triangles, triangle2, new Edge(b, c), point);
                updateRule(triangles, triangle3, new Edge(c, a), point);
            }
        }

        triangles.removeTrianglesContainingVertex(superTriangle.a);
        triangles.removeTrianglesContainingVertex(superTriangle.b);
        triangles.removeTrianglesContainingVertex(superTriangle.c);

        return triangles.getTriangles();
    }

    private static void updateRule(Triangles triangles, Triangle triangle, Edge edge, Vector newVertex) {
        Triangle neighbourTriangle = triangles.findNeighbour(triangle, edge);

        if (neighbourTriangle != null) {
            if (neighbourTriangle.isPointInCircumscribedCircle(newVertex)) {
                triangles.remove(triangle);
                triangles.remove(neighbourTriangle);

                Vector noneEdgeVertex = neighbourTriangle.getOppositeVertexFromEdge(edge);

                Triangle firstTriangle = new Triangle(noneEdgeVertex, edge.a, newVertex);
                Triangle secondTriangle = new Triangle(noneEdgeVertex, edge.b, newVertex);

                triangles.addAll(firstTriangle, secondTriangle);

                updateRule(triangles, firstTriangle, new Edge(noneEdgeVertex, edge.a), newVertex);
                updateRule(triangles, secondTriangle, new Edge(noneEdgeVertex, edge.b), newVertex);
            }
        }
    }

    private static Triangle getSuperTriangle() {
        Vector p1 = new Vector(0, 0);
        Vector p2 = new Vector(0, 2 * Controller.HEIGHT);
        Vector p3 = new Vector(2 * Controller.WIDTH, 0);

        return new Triangle(p1, p2, p3);
    }
}