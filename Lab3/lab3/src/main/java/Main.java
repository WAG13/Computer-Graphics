import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Point> vertex = inputData("vertex.txt");
        ArrayList<Point> edges = inputData("edges.txt");
        Point pointToLocate = new Point(15, 10);

        ArrayList<Integer> sortedVertex = getSortedVertex(vertex);

        for (int i = 0; i < sortedVertex.size(); i++) {
            System.out.println(sortedVertex.get(i));
        }

        ArrayList<Strip> strips = getStrips(vertex, edges, sortedVertex);

        System.out.println();
        for (int i = 0; i < strips.size(); i++) {
            System.out.print(i + " : ");
            System.out.println(strips.get(i).edges);
        }
        System.out.println();
 /*
        Scanner sc = new Scanner(System.in);
        while (true) {
            p.x = sc.nextInt();
            p.y = sc.nextInt();
*/
            if (pointToLocate.y < strips.get(0).getPointBottom().y || pointToLocate.y > strips.get(strips.size() - 1).getPointTop().y) {
                System.out.println("strips : -1");
            } else {
                Strip strip = getStrip(pointToLocate, strips);
                Point between = strip.getEdges(pointToLocate, vertex, edges);

                if (between.x == -1 || between.y == -1) { //out of any of strips
                    System.out.println("not in graph");
                } else {
                    //edges numbers
                    System.out.println("BETWEEN EDGES NUMBER: " + between.x + " AND " + between.y);
                    int edgeNum1 = between.x, edgeNum2 = between.y;

                    System.out.println(vertex.get(edges.get(edgeNum1).x).toString() + ' ' + vertex.get(edges.get(edgeNum1).y).toString() +
                            '\n' + (vertex.get(edges.get(edgeNum2).x).toString() + ' ' + vertex.get(edges.get(edgeNum2).y).toString()));
                }
            }

        ArrayList<Point2D> pointsPoint2D = new ArrayList<>();
        for(Point v : vertex) pointsPoint2D.add(new Point2D.Double(v.getX(), v.getY()));
        ArrayList<Line2D> pointsEdges2D = new ArrayList<>();
        for(Point e : edges) pointsEdges2D.add(new Line2D.Double(vertex.get((int) e.getX()).getX(),
                vertex.get((int) e.getX()).getY(),
                vertex.get((int) e.getY()).getX(),
                vertex.get((int) e.getY()).getY()));
        ArrayList<Line2D> pointsEdges2DAnswer = new ArrayList<>();
        FigureDrawer figureDrawer = new FigureDrawer("Strip", pointsEdges2D, pointsPoint2D, strips,
                new Point2D.Double(pointToLocate.getX(), pointToLocate.getY()),
                pointsEdges2DAnswer);
        //}
    }

    public static ArrayList<Point> inputData(String filename) throws FileNotFoundException {
        ArrayList<Point> arr = new ArrayList<>();

        Scanner scFile = new Scanner(new File(filename));
        while (scFile.hasNextInt()) {
            Point tmp = new Point();
            tmp.x = scFile.nextInt();
            tmp.y = scFile.nextInt();
            arr.add(tmp);
        }
        return arr;
    }

    public static ArrayList<Integer> getSortedVertex(ArrayList<Point> vertex) {
        ArrayList<Integer> arr = new ArrayList<>();
        int maxIndex = 0;
        for (int i = 1; i < vertex.size(); i++) {
            if (vertex.get(i).y > vertex.get(maxIndex).y)
                maxIndex = i;
        }
        int minIndex;
        int minY = Integer.MIN_VALUE;
        for (int i = 0; i < vertex.size(); i++) {
            minIndex = maxIndex;
            for (int j = 0; j < vertex.size(); j++) {
                if (vertex.get(j).y > minY && vertex.get(j).y < vertex.get(minIndex).y) {
                    minIndex = j;
                }
            }
            arr.add(minIndex);
            minY = vertex.get(minIndex).y;
            if (minIndex == maxIndex) return arr;
        }

        return arr;
    }

    static boolean crossStrip(Strip strip, ArrayList<Point> vertex, Point edge) {
        int downY, upY;
        downY = min(vertex.get(edge.x).y, vertex.get(edge.y).y);
        upY = max(vertex.get(edge.x).y, vertex.get(edge.y).y);
        if (strip.getPointTop().y <= downY || strip.getPointBottom().y >= upY)
            return false;
        return true;
    }

    static ArrayList<Strip> getStrips(ArrayList<Point> vertex, ArrayList<Point> edges, ArrayList<Integer> sortedVertex) {
        ArrayList<Strip> arr = new ArrayList<>();

        for (int i = 1; i < sortedVertex.size(); i++) {
            Strip strip = new Strip(sortedVertex.get(i - 1), sortedVertex.get(i), vertex);
            for (int j = 0; j < edges.size(); j++) {
                if (crossStrip(strip, vertex, edges.get(j)))
                    strip.edges.add(j);
            }
            strip.sort(vertex, edges);
            arr.add(strip);
        }

        return arr;
    }

    static Strip getStrip(Point p, ArrayList<Strip> strips) {
        int top = strips.size() - 1;
        int bottom = 0;
        int mid = (top + bottom) / 2;
        Strip strip = strips.get(mid);
        int tempComputation = strip.contain(p);
        while (tempComputation != 0) {
            if (tempComputation == -1) {
                top = mid;
                mid = (top + bottom) / 2;
            } else {
                if (bottom == mid) {
                    mid = top;
                } else {
                    bottom = mid;
                    mid = (top + bottom) / 2;
                }

            }
            strip = strips.get(mid);
            tempComputation = strip.contain(p);
        }
        System.out.println("STRIP : " + mid);
        return strip;
    }
}
