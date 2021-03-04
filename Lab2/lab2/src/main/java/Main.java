import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import drawer.FigureDrawer;
import graph.*;
import graph.exeptions.OutOfGraphException;

public class Main {
    private static float pointToLocateX = 0;
    private static float pointToLocateY = -5;

    public static void main(String[] args) {

        Point pointToLocate = new Point(pointToLocateX, pointToLocateY);
        pointToLocate = new Point(10, 30);
        //pointToLocate = new Point(20, 0);
        //pointToLocate = new Point(0, -5);

        Point[] points = {
                new Point(10, -40),
                new Point(50, -30),
                new Point(-20, -10),
                new Point(20, 0),
                new Point(50, 20),
                new Point(-30, 30),
                new Point(10, 30),
                new Point(50, 40),
                new Point(0, 60)};

        boolean[][] matrix = {
                {false, true, true, true, true, false, false, false, false},
                {true, false, false, false, true, false, false, false, false},
                {true, false, false, true, false, true, true, false, false},
                {true, false, true, false, true, false, true, false, false},
                {true, true, false, true, false, false, true, true, false},
                {false, false, true, false, false, false, false, false, true},
                {false, false, true, true, true, false, false, true, true},
                {false, false, false, false, true, false, true, false, true},
                {false, false, false, false, false, true, true, true, false} };

        Graph graph = new Graph(points, matrix);
        List<Edge> area = new ArrayList<>();

        try {
            area = graph.algo(pointToLocate);
        } catch (OutOfGraphException e) {
            System.out.println("Point is out of the graph");
        }

        ArrayList<Line2D> edgesLine2DAnswer = new ArrayList<>();
        for(Edge e : area) {
            System.out.println("(" + e.getAx() + ", " + e.getAy() + "), " + "(" + e.getBx() + ", " + e.getBy() + ")");
            edgesLine2DAnswer.add(new Line2D.Double(e.getAx(), e.getAy(), e.getBx(), e.getBy()));
        }

        ArrayList<Line2D> edgesLine2D = new ArrayList<>();
        for (Chain c : graph.getChains()){
        for (Edge e : c.getChain()){
            edgesLine2D.add(new Line2D.Double(e.getAx(), e.getAy(), e.getBx(), e.getBy()));
        }}
        FigureDrawer figureDrawer = new FigureDrawer("Chain method", edgesLine2D, true,
                new Point2D.Double(pointToLocate.getX(), pointToLocate.getY()), edgesLine2DAnswer);

    }
}
