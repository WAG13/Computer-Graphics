import algorithm.*;
import drawer.*;
import polygon.*;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/***
 * Main class for solving point location problem.
 * Points the polygon to be constructed are read from file.
 * The point you want to check is set with its coordinates (pointToLocateX, pointToLocateY).
 *
 * Path to file must be set properly.
 * File syntax:
 * 1) each point is written as "(x,y)" without qoutes
 * 2) each point is written in a new line
 * 3) decimal separator is point sign '.' (eg 12.2)
 * Correct syntax example: (12.3,0.9)
 *
 * Answer is shown in console and equals "true" if point locates in polygon
 * and "false" if not
 *
 * Graph is shown to ensure you the algorithm works correctly.
 */

public class Main {

    private static double pointToLocateX = 50;
    private static double pointToLocateY = 70;

    public static void main(String[] args) {
        Point pointToLocate = new Point(pointToLocateX, pointToLocateY);
        //Point inside polygone
        pointToLocate = new Point(50, 60);
        //Point outside polygone
        pointToLocate = new Point(40, 20);
        //Point on the edge
        pointToLocate = new Point(50, 30);
        //Point at the point
        pointToLocate = new Point(30, 50);

        PointReader pr = new PointReader("src/main/resources/points.txt");

        ArrayList<Point> pointList = pr.getPoints();
        ArrayList<Edge> edgeList = new ArrayList<>();

        try{
            for (int i = 0; i < pointList.size()-1; i++){
                edgeList.add(new Edge(pointList.get(i), pointList.get(i+1)));
            }
            edgeList.add(new Edge(pointList.get(pointList.size()-1), pointList.get(0)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Polygon polygon = new Polygon(edgeList);


        boolean locatesInPolygon = Algorithm.pointLocatesInSimplePolygon(polygon, pointToLocate);
        System.out.println(locatesInPolygon);

        //draw
        ArrayList<Line2D> edgesLine2D = new ArrayList<>();
        for (Edge e : edgeList){
            edgesLine2D.add(new Line2D.Double(e.getBegin().getX(), e.getBegin().getY(), e.getEnd().getX(), e.getEnd().getY()));
        }
        FigureDrawer figureDrawer = new FigureDrawer("Polygon and point", edgesLine2D, true,
                new Point2D.Double(pointToLocate.getX(), pointToLocate.getY()), locatesInPolygon);
    }
}
