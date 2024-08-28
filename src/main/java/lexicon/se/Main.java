package lexicon.se;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        City newCity = new City(0, "New York", "USA", "New York", 8419000);
        CityDaoJDBC cityDao = new CityDaoJDBC();
        City addedCity = cityDao.add(newCity);
        System.out.println("Added City: " + addedCity);


        if (addedCity != null) {
            System.out.println("City added successfully: " + addedCity);
        } else {
            System.out.println("Failed to add city.");
        }
    }
}
