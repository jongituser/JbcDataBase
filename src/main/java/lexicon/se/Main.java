package lexicon.se;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        // Create an instance of the data access object (DAO)
        CityDaoJDBC citydaojdbc = new CityDaoJDBC();

        City newCity = new City (0, "Far Cry", "EGA", "District 1", 1234561);

        City addedCity = citydaojdbc.add(newCity);


        if (addedCity != null) {
            System.out.println("City added successfully: " + addedCity);
        } else {
            System.out.println("Failed to add city.");
        }
    }
}
