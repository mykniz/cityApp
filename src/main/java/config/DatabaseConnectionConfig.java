package config;

import dao.CityDao;
import entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionConfig {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    // static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    Connection conn;
    Statement stmt;

    private void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void createTable() {
        try {
            initDatabase();
            System.out.println("Creating table...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS   CITY " +
                    " (cityId INTEGER not null AUTO_INCREMENT,    " +
                    " name VARCHAR(255) not null, " +
                    " region VARCHAR(255) not null, " +
                    " district VARCHAR(255) not null, " +
                    " population INTEGER not null, " +
                    " foundation VARCHAR(255) not null," +
                    "PRIMARY KEY (cityId)) ";
            stmt.executeUpdate(sql);
            System.out.println("Created table...");
            stmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException classNotFoundException) {
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
        String sql = "INSERT INTO CITY (name, region, district, population, foundation ) Values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        List<City> cityFromFile = cityDao.getParsedCityList();

        for (City c: cityFromFile) {
            preparedStatement.setString(1, c.getName());
            preparedStatement.setString(2,c.getRegion());
            preparedStatement.setString(3,c.getDistrict());
            preparedStatement.setInt(4,c.getPopulation());
            preparedStatement.setString(5,c.getFoundation());
            preparedStatement.executeUpdate();
        }
        conn.close();
    }


    public void getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM CITY";
        initDatabase();
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
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
        String sql = "DROP TABLE CITY ";
        stmt.executeUpdate(sql);
        System.out.println("table dropped");
        stmt.close();
        conn.close();
    }

    public void truncateTable() throws SQLException, ClassNotFoundException {
        initDatabase();
        stmt = conn.createStatement();
        String sql = "TRUNCATE TABLE CITY ";
        stmt.executeUpdate(sql);
        System.out.println("table truncated");
        stmt.close();
        conn.close();
    }
}


