package ch.laessker.jonathan.airhockey;

import java.util.ArrayList;

/**
 * Created by Roman Lenov on 22.04.2017.
 */

public class HighScorePoints {
    private ArrayList<Integer> points;
    private ArrayList<String> names;

    public void HighScore(){
        points = new ArrayList<>();
        names = new ArrayList<>();
    }

    public void setEntry(int point, String name){
        if(points.size() > 2 || names.size() > 2)
            throw new IllegalStateException("HighScore hat zu viele Einträge");
        names.add(name);
        points.add(point);
    }

    public ArrayList<String> getNames(){
        return names;
    }

    public ArrayList<Integer> getPoints(){
        return points;
    }
}
