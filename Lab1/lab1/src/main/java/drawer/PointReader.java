package drawer;

import polygon.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
(x,y) - point
x,y - point
*/

public class PointReader {
    private ArrayList<Point> pointList;

    public PointReader(String filepath){
        pointList = new ArrayList<>();
        String []xy;
        try{
            Scanner scanner = new Scanner(new File(filepath));
            while(scanner.hasNext()){
                String s = scanner.nextLine();
                xy = s.split("[)(,]");
                pointList.add(new Point(Double.parseDouble(xy[1]), Double.parseDouble(xy[2])));
                System.out.println(new Point(Double.parseDouble(xy[1]), Double.parseDouble(xy[2])));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Point> getPoints(){
        return pointList;
    }
}
