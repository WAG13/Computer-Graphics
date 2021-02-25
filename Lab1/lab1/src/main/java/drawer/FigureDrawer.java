package drawer;

import polygon.Constants;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Visualize a set of line segments.
 * Segment is determined with 2 points.
 * The frame shown fits your screen dimension and covers your viewport.
 * The origin of cartesian coordinate system locates in the center of your viewport.
 *
 * Point is the segment with the same ends. Point fill 1px.
 *
 * Class not do not process any errors. Parameters must be passed strictly.
 * */
public class FigureDrawer extends JPanel{
    /**
     * @param title title of frame
     * @param edges lines you want to show
     * @param point point you want to show, has 5px width and height
     * */

    public FigureDrawer(String title, ArrayList<Line2D> edges, boolean showAxis, Point2D point, boolean locatesInPolygon) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = 700;
        int screenWidth = 800;
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(){
            Graphics2D g2;

            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.translate(screenWidth / 4,screenHeight * 3 / 4);
                g2.scale(3.0, -3.0);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //draw Axis
                g.setColor(Color.LIGHT_GRAY);
                if (showAxis){
                    for (int i=0; i<screenWidth/Constants.SCALE ;i++){
                        g2.draw(new Line2D.Double(-screenWidth,-i*Constants.SCALE,screenWidth,-i*Constants.SCALE));
                        g2.draw(new Line2D.Double(-screenWidth,i*Constants.SCALE,screenWidth,i*Constants.SCALE));
                        g2.draw(new Line2D.Double(-i*Constants.SCALE,-screenHeight,-i*Constants.SCALE,screenHeight));
                        g2.draw(new Line2D.Double(i*Constants.SCALE,-screenHeight,i*Constants.SCALE,screenHeight));
                    }
                    g.setColor(Color.DARK_GRAY);
                    g2.draw(new Line2D.Double(-screenWidth,0,screenWidth,0));
                    g2.draw(new Line2D.Double(0,-screenHeight,0,screenHeight));
                }

                //draw Polygon
                g2.setColor(Color.BLUE);
                for (Line2D l : edges){
                    g2.draw(l);
                }

                //draw Point
                Color pointColor = locatesInPolygon ? Color.GREEN : Color.RED;
                g2.setColor(pointColor);

                g2.fill(new Ellipse2D.Double(point.getX() -2 , point.getY() - 2,4,4));
            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
