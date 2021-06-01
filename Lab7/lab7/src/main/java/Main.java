
import drawer.FigureDrawer;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String args[]) throws FileNotFoundException {
        try (Scanner sc = new Scanner(System.in)) {
            ArrayList<Point> points = Preparat.inputDatas("input.txt");

            ArrayList<Point> p = Preparat.preperat(points);
            System.out.println("\n\nTHE POINTS IN THE HULL:");
            for (Point point : p) {
                System.out.println(point.x + " " + point.y);
            }
            p = Preparat.addPreperat(p, new Point(5,2));
            FigureDrawer figureDrawer = new FigureDrawer("Preparat", points, p);

        }
    }
}