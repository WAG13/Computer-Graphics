package algorithm;

import java.util.*;
import java.util.stream.Collectors;

class Triangles {

    private List<Triangle> triangles;

    Triangles() {
        this.triangles = new ArrayList<>();
    }

    void addAll(Triangle... trianglesToAdd) {
        triangles.addAll(Arrays.asList(trianglesToAdd));
    }

    void add(Vector a, Vector b, Vector c) {
        this.triangles.add(new Triangle(a, b, c));
    }

    void remove(Triangle triangle) {
        this.triangles.remove(triangle);
    }

    List<Triangle> getTriangles() {
        return this.triangles;
    }

    Triangle findContainingTriangle(Vector point) {
        for (Triangle triangle : triangles) {
            if (triangle.contains(point)) {
                return triangle;
            }
        }
        return null;
    }

    Triangle findNeighbour(Triangle triangle, Edge edge) {
        for (Triangle triangleFromSoup : triangles) {
            if (triangleFromSoup.isAdjacentEdge(edge) && triangleFromSoup != triangle) {
                return triangleFromSoup;
            }
        }
        return null;
    }

    Triangle findTriangleSharing(Edge edge) {
        for (Triangle triangle : triangles) {
            if (triangle.isAdjacentEdge(edge)) {
                return triangle;
            }
        }
        return null;
    }

    Edge findNearestEdge(Vector point) {
        return triangles.stream()
                .map(triangle -> triangle.findNearestEdge(point))
                .min(Comparator.comparing(Map.Entry::getKey))
                .orElseThrow()
                .getValue();
    }

    void removeTrianglesContainingVertex(Vector vertex) {
        List<Triangle> trianglesToBeRemoved = triangles.stream()
                .filter(triangle -> triangle.hasVertex(vertex))
                .collect(Collectors.toList());

        triangles.removeAll(trianglesToBeRemoved);
    }

}