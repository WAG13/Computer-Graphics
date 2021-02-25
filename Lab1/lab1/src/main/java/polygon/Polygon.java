package polygon;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge> edgeList;

    public Polygon(){
        edgeList = new ArrayList<Edge>();
    }

    public Polygon(ArrayList<Edge> edges){
        if (edges != null){
            edgeList = edges;
        }
        else {
            edgeList = new ArrayList<Edge>();
        }
    }

    public ArrayList<Edge> getEdges(){
        return edgeList;
    }
}
