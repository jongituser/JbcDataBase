package lexicon.se;
import lexicon.se.helper.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CityDaoJDBC implements CityDao {

    @Override
    public City findById(int id) {
        City city = null;
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from city where id = ?")
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    int ID = resultSet.getInt(1);
                    String Name = resultSet.getString(2);
                    String CountryCode = resultSet.getString(3);
                    String District = resultSet.getString(4);
                    int Population = resultSet.getInt(5);
                    city = new City(ID, Name, CountryCode, District, Population);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return city;
    }

    @Override
    public List<City> findByCode(String code) {

        List<City> cityList = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE CountryCode = ?")
        ) {
            preparedStatement.setString(1, code);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt(1);
                    String Name = resultSet.getString(2);
                    String CountryCode = resultSet.getString(3);
                    String District = resultSet.getString(4);
                    int Population = resultSet.getInt(5);
                    City city = new City(ID, Name, CountryCode, District, Population);

                    cityList.add(city);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityList;
    }

    @Override
    public List<City> findByName(String name) {

        List<City> cityList = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city WHERE Name = ?")
        ) {
            preparedStatement.setString(1, name);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt(1);
                    String Name = resultSet.getString(2);
                    String CountryCode = resultSet.getString(3);
                    String District = resultSet.getString(4);
                    int Population = resultSet.getInt(5);
                    City city = new City(ID, Name, CountryCode, District, Population);

                    cityList.add(city);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityList;
    }

    @Override
    public List<City> findAll() {
        List<City> cityList = new ArrayList<>();

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM city");
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String countryCode = resultSet.getString(3);
                String district = resultSet.getString(4);
                int population = resultSet.getInt(5);


                City city = new City(id, name, countryCode, district, population);


                cityList.add(city);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return cityList;
    }

    @Override
    public City add(City city){
        String query = "INSERT INTO City (Name, CountryCode, District, Population) VALUES (?, ?, ?, ?)";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountrycode()); // Ensure this is valid
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("City created successfully");

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedCityId = generatedKeys.getInt(1);
                        city.setId(generatedCityId);
                        return city;
                    } else {
                        System.out.println("Could not retrieve generated City ID");
                    }
                }
            } else {
                System.out.println("City creation failed, no rows inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if insertion failed
    }

    @Override
    public City update(City city) {

        String query = "UPDATE City SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {


            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountrycode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.setInt(5, city.getId());

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();


            if (rowsUpdated > 0) {
                System.out.println("City updated successfully");
                return city;
            } else {
                System.out.println("City update failed");
                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int delete(City city) {
        {

            String query = "DELETE FROM City WHERE ID = ?";

            try (Connection connection = MySQLConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {


                preparedStatement.setInt(1, city.getId());


                int rowsDeleted = preparedStatement.executeUpdate();


                if (rowsDeleted > 0) {
                    System.out.println("City deleted successfully");
                }
                else
                {
                    System.out.println("City not found or not deleted");
                }

                return rowsDeleted;

            } catch (SQLException e) {
                // Handle SQL exceptions
                e.printStackTrace();
                return 0; // Return 0 in case of an error
            }
        }
    }
}
