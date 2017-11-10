import static Controller.CineplexManager.*;

import Controller.DataManager;
import Model.Cinema;
import Model.Constant.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestWriteCinemaList {
    public static void main(String[] args) {
        HashMap<Cineplex, ArrayList<Cinema>> cinemaList = new HashMap<>();

        cinemaList.put(Cineplex.JEM, new ArrayList<>());
        cinemaList.put(Cineplex.TheCathay, new ArrayList<>());

        cinemaList.get(Cineplex.JEM).add(new Cinema(Cineplex.JEM,true, MovieType.ThreeD, "XYZ", 10));
        cinemaList.get(Cineplex.JEM).add(new Cinema(Cineplex.JEM,false, MovieType.Digital, "ACS", 10));

        cinemaList.get(Cineplex.TheCathay).add(new Cinema(Cineplex.TheCathay,true, MovieType.ThreeD, "SAD", 10));
        cinemaList.get(Cineplex.TheCathay).add(new Cinema(Cineplex.TheCathay,false, MovieType.Digital, "VSA", 10));

        try {
            DataManager.writeSerializedObject("res/data/cinemaList.dat", cinemaList);
            System.out.println("Success");
        }
        catch (IOException ex) {
            System.out.println("Fail");
        }
    }
}