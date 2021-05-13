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

        //LaunchClass.run();

        CityParserService cityParserService = new CityParserService(new CityDao());
        CityDao cityDao = cityParserService.parseFileToObject(FilePathConfig.getFileName());

        DatabaseConnectionConfig databaseConnectionConfig = new DatabaseConnectionConfig();
//      databaseConnectionConfig.truncateTable();
        databaseConnectionConfig.dropTable();
        databaseConnectionConfig.createTable();
        databaseConnectionConfig.insertData(cityDao);
        databaseConnectionConfig.getData();
    }
}



