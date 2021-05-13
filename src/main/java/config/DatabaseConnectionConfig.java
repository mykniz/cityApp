package config;

import dao.CityDao;
import entity.City;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseConnectionConfig {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CITY (cityId INTEGER not null AUTO_INCREMENT," +
            "name VARCHAR(255) not null," +
            "region VARCHAR(255) not null," +
            "district VARCHAR(255) not null," +
            "population INTEGER not null," +
            "foundation VARCHAR(255) not null," +
            "PRIMARY KEY (cityId)) ";

    private static final String SQL_INSERT_INTO_TABLE = "INSERT INTO CITY (name, region, district, population, foundation ) Values (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT * FROM CITY";
    private static final String SQL_DROP_TABLE = "DROP TABLE CITY";
    private Connection conn;
    private Statement stmt;

    private void initDatabase() {
        try (InputStream inputStream = new FileInputStream("/Users/a19188807/IdeaProjects/sberRepo-main/src/main/resources/db.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String dbUrl = properties.getProperty("db.url");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");
            String dbDriverClassName = properties.getProperty("db.driverClassName");

            Class.forName(dbDriverClassName);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new IllegalStateException();
        }
    }

    public void createTable() {
        try {
            initDatabase();
            System.out.println("Creating table...");
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL_CREATE_TABLE);
            System.out.println("Created table...");
            stmt.close();
            conn.close();

        } catch (SQLException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void insertData(CityDao cityDao) throws SQLException, ClassNotFoundException {
        initDatabase();
        System.out.println("Inserting data into table");
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_INTO_TABLE);
        List<City> cityFromFile = cityDao.getParsedCityList();
        for (City c : cityFromFile) {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setString(2, c.getRegion());
            preparedStatement.setString(3, c.getDistrict());
            preparedStatement.setInt(4, c.getPopulation());
            preparedStatement.setString(5, c.getFoundation());
            preparedStatement.executeUpdate();
        }
        conn.close();
    }

    public void getData() throws SQLException, ClassNotFoundException {
        initDatabase();
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(SQL_SELECT_ALL);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while (resultSet.next()) {
            for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
                System.out.print(resultSet.getString(i) + "; ");
            }
            System.out.println();
        }
        System.out.println("table revealed");
        stmt.close();
        conn.close();

    }

    public void dropTable() throws SQLException, ClassNotFoundException {
        initDatabase();
        stmt = conn.createStatement();
        stmt.executeUpdate(SQL_DROP_TABLE);
        System.out.println("table dropped");
        stmt.close();
        conn.close();
    }
}


