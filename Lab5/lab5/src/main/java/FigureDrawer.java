import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class FigureDrawer extends JComponent {

    private ArrayList<Point> points;
    private LinkedList<Point> convexHull;

    private Image image;
    private Graphics2D graphics2D;

    private final int PAINT_RADIUS = 3;

    public FigureDrawer() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(e.getPoint());
                graphics2D.fillOval(e.getX()-PAINT_RADIUS, e.getY()-PAINT_RADIUS, PAINT_RADIUS*2, PAINT_RADIUS*2);
                repaint();
            }
        });
    }

    public void redrawDots() {
        clear();
        graphics2D.setPaint(Color.black);
        for (Point e : points) {
            graphics2D.fillOval((int) (e.getX() - PAINT_RADIUS), (int) (e.getY()-PAINT_RADIUS), PAINT_RADIUS*2, PAINT_RADIUS*2);
            repaint();
        }
    }

    public void clearData() {
        clear();
        initialize();
    }

    public void createConvexHull() {
        redrawDots();
        QuickHull quickHull = new QuickHull();

        convexHull = quickHull.createConvexHull(points);
        graphics2D.setPaint(Color.red);
        Point left;
        Point right;
        if(convexHull.size() > 1) {
            left = convexHull.get(0);
            right = convexHull.get(1);
            Point leftRightVector = new Point(right.x - left.x, right.y - left.y);
            List<Point> upperPoints = new ArrayList<>();
            List<Point> lowerPoints = new ArrayList<>();
            for (int i = 2; i < convexHull.size(); i++){
                if(quickHull.isOnTheLeftSide(
                        new Point(convexHull.get(i).x - left.x, convexHull.get(i).y - left.y), leftRightVector)){
                    upperPoints.add(convexHull.get(i));
                }else{
                    lowerPoints.add(convexHull.get(i));
                }
            }
            upperPoints.sort(Comparator.comparing(Point::getX));
            lowerPoints.sort(Comparator.comparing(Point::getX));
            drawHull(upperPoints, left, right);
            drawHull(lowerPoints, left, right);

        }

        for (Point point : convexHull) {
            graphics2D.fillOval(point.x - PAINT_RADIUS, point.y - PAINT_RADIUS, PAINT_RADIUS * 2, PAINT_RADIUS * 2);
        }
        graphics2D.setPaint(Color.black);
        repaint();
    }
    public void drawHull(List<Point> list, Point left, Point right){
        for(int i = 0; i < list.size() - 1; i++){
            graphics2D.drawLine(list.get(i).x, list.get(i).y, list.get(i + 1).x, list.get(i + 1).y);
        }
        if(list.size() > 0) {
            graphics2D.drawLine(left.x, left.y, list.get(0).x, list.get(0).y);
            graphics2D.drawLine(right.x, right.y, list.get(list.size() - 1).x, list.get(list.size() - 1).y);
        }
        else{
            graphics2D.drawLine(right.x, right.y, left.x, left.y);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        if(image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clearData();
        }
        g.drawImage(image, 0, 0, null);
    }

    private void  initialize() {
        points = new ArrayList<>();
        graphics2D.setPaint(Color.black);
    }

    private void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }
}
