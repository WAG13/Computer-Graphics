import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

class CustomComparator implements Comparator<Integer> {

    static int indexTop, indexBottom;
    static Point pointTop, pointBottom;
    static ArrayList<Point> vertex, allEdges;

    private double getPointCross(Point a, Point b, int line) {
        return a.x + 1.0 * (line - a.y) * (b.x - a.x) / (b.y - a.y);
    }

    private double getPointCross(Integer j, int line, ArrayList<Point> vertex, ArrayList<Point> allEdges) {
        return getPointCross(vertex.get(allEdges.get(j).x), vertex.get(allEdges.get(j).y), line);
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        if (getPointCross(o1, pointBottom.y, vertex, allEdges) > getPointCross(o2, pointBottom.y, vertex, allEdges) ||
                getPointCross(o1, pointTop.y, vertex, allEdges) > getPointCross(o2, pointTop.y, vertex, allEdges))
            return 1;
        return 0;
    }
}
