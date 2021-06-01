package drawer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;

public class FigureDrawer extends JPanel{

    private final static int SCALE=10;
    public FigureDrawer(String title, ArrayList<Point> inputPoints, ArrayList<Point> pAnswer) {
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
                g2.translate(screenWidth / 4,screenHeight / 2);
                g2.scale(3.0, -3.0);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                ArrayList<Point2D> points = new ArrayList<>();
                for(Point v : inputPoints) points.add(new Point2D.Double(v.getX()*SCALE, v.getY()*SCALE));

                ArrayList<Point2D> answer = new ArrayList<>();
                for(Point v : pAnswer) answer.add(new Point2D.Double(v.getX()*SCALE, v.getY()*SCALE));

                ArrayList<Line2D> edges = new ArrayList<>();
                for (int i = 0; i < pAnswer.size(); i++) {
                    Point v = pAnswer.get(i);
                    Point u = (i+1 == pAnswer.size()) ? pAnswer.get(0) : pAnswer.get(i+1);
                    edges.add(new Line2D.Double(v.getX()*SCALE, v.getY()*SCALE, u.getX()*SCALE, u.getY()*SCALE));
                }


                //draw Axis
                g.setColor(Color.LIGHT_GRAY);
                for (int i=0; i<screenWidth/SCALE ;i++){
                    g2.draw(new Line2D.Double(-screenWidth,-i*SCALE,screenWidth,-i*SCALE));
                    g2.draw(new Line2D.Double(-screenWidth,i*SCALE,screenWidth,i*SCALE));
                    g2.draw(new Line2D.Double(-i*SCALE,-screenHeight,-i*SCALE,screenHeight));
                    g2.draw(new Line2D.Double(i*SCALE,-screenHeight,i*SCALE,screenHeight));
                }
                g.setColor(Color.DARK_GRAY);
                g2.draw(new Line2D.Double(-screenWidth,0,screenWidth,0));
                g2.draw(new Line2D.Double(0,-screenHeight,0,screenHeight));

                //draw Hull
                for (Line2D l : edges){

                    g2.setColor(Color.CYAN);
                    g2.draw(l);
                }
                //draw Points
                for (Point2D p : points){
                    g2.setColor(Color.BLUE);
                    g2.fill(new Ellipse2D.Double(p.getX() -2 , p.getY() - 2,4,4));
                }

                //draw Hull Points
                for (Point2D p : answer){
                    g2.setColor(Color.GREEN);
                    g2.fill(new Ellipse2D.Double(p.getX() -2 , p.getY() - 2,4,4));
                }

            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}