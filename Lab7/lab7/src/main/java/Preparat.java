import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preparat {

    static public ArrayList<Point> preperat(ArrayList<Point> points) {
        ArrayList<Point> hull = new ArrayList<>();

        if (points.size() < 3)
            return (ArrayList) points.clone();
        if(points.size() == 3){
            // points lies on the same line
            if (pointLocation(points.get(0),points.get(1),points.get(2)) == 0){
                hull.add(points.get(findLeft(points)));
                hull.add(points.get(findRight(points)));
                return hull;
            } else {
                if (pointLocation(points.get(0),points.get(1),points.get(2)) < 0){
                    return points;
                }
                hull.add(points.get(0));
                hull.add(points.get(2));
                hull.add(points.get(1));
                return hull;
            }
        } else {
            for (int i = 1; i < points.size(); i++)
                hull.add(points.get(i));

            hull = preperat(hull);
            System.out.println("Hull on prev step");
            for (Point point : hull) {
                System.out.println(point.x + " " + point.y);
            }

            return addPreperat(hull, points.get(0));
        }
    }

    static public ArrayList<Point> addPreperat(ArrayList<Point> points, Point newPoint){
        int B = findBottom(points,newPoint);
        int T = findTop(points,newPoint);

        //the newPoint lies INSIDE of alredy built convex hull
        if(B < 0 || T < 0) {
            return points;
        };
        return addBetween(points,newPoint,T,B);
    }

    static public ArrayList<Point> addBetween(ArrayList<Point> points, Point newPoint, int first, int second) {
        if(first > second){
            ArrayList<Point> hull = new ArrayList<>();
            hull.add(newPoint);
            for (int i = second; i < first;i++)
                hull.add(points.get(i));
            hull.add(points.get(first));
            return hull;
        } else{
            ArrayList<Point> hull = new ArrayList<>();
            for (int i = 0; i < first;i++)
                hull.add(points.get(i));
            hull.add(points.get(first));
            hull.add(newPoint);
            for (int i = second; i < points.size();i++)
                hull.add(points.get(i));
            return hull;
        }
    }

    static public int findRight(ArrayList<Point> points){
        int temp = 0;
        for (int i = 0; i < points.size(); i++){
            if(points.get(temp).x < points.get(i).x) temp = i;
        }
        return temp;
    }

    static public int findLeft(ArrayList<Point> points){
        int temp = 0;
        for (int i = 0; i < points.size(); i++){
            if(points.get(temp).x > points.get(i).x) temp = i;
        }
        return temp;
    }

    static public int findBottom(ArrayList<Point> points, Point osn){
        int len = points.size();
        for (int i = 0; i < len; i++){
            if(pointLocation(osn,points.get(i),points.get((i + 1) % len)) <= 0
                    && pointLocation(osn,points.get(i),points.get((i - 1 + len) % len)) <= 0) return i;
        }
        return -1;
    }

    static public int findTop(ArrayList<Point> points, Point osn){
        int len = points.size();
        for (int i = 0; i < len; i++){
            //якщо і+1 точка лежить зліва від прямої утвореної і-ю точкою та новою точкою
            //то тоді пряма утворена цими точками є верхньою опорною прямою
            if(pointLocation(osn, points.get(i), points.get((i + 1) % len)) >= 0
                    && pointLocation(osn,points.get(i),points.get((i - 1 + len) % len)) >= 0) return i;
        }
        return -1;
    }

    static public int pointLocation(Point A, Point B, Point P)
    {
        int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0) return 1;
        else if (cp1 == 0) return 0;
        else return -1;
    }

    public static ArrayList<Point> inputDatas(String filename) throws FileNotFoundException {
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
}
