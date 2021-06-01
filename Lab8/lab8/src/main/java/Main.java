import drawer.FigureDrawer;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        ArrayList<Point> points = PolygonConvexHull.inputDatas("input.txt");

        ArrayList<Point> convexHull = PolygonConvexHull.build(points);
        System.out.println("HULL POINTS:");
        for (Point point : convexHull) {
            System.out.println(point.x + " " + point.y);
        }
        FigureDrawer figureDrawer = new FigureDrawer("Li", points, convexHull);
    }
}