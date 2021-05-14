import config.DatabaseConnectionConfig;
import config.FilePathConfig;
import dao.CityDao;
import launch.LaunchClass;
import service.CityParserService;
import service.CitySorterService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException, ClassNotFoundException {

        /**
         parse data to dao
         */
     //   LaunchClass.run();



        /**
         * work with H2 database
         */

        DatabaseConnectionConfig databaseConnectionConfig = new DatabaseConnectionConfig();
        databaseConnectionConfig.dropTable();
        databaseConnectionConfig.createTable();
        databaseConnectionConfig.insertData(LaunchClass.cityDao);
        databaseConnectionConfig.getData();
    }
}



