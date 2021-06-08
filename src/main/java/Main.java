import config.DatabaseConnectionConfig;
import launch.LaunchClass;
import java.io.FileNotFoundException;
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



