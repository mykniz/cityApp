package launch;

import config.FilePathConfig;
import dao.CityDao;
import service.CityParserService;
import service.CitySorterService;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LaunchClass {

    public static CityDao cityDao;

    public static void run() throws FileNotFoundException {

        CityParserService cityParserService = new CityParserService(new CityDao());

        System.out.println("---------------------------------------");
        System.out.println("Welcome to City data management program");
        System.out.println("---------------------------------------");
        System.out.println("Please, upload the origin file to begin");
        System.out.println("To upload the file press 0...");

        Scanner initScanner = new Scanner(System.in);
        int upload = initScanner.nextInt();
        if (upload != 0) {
            System.out.println("Sorry, but you should press 0 to begin...");
        }
        if (upload == 0) {
            System.out.println("File uploaded successfully");
            cityDao = cityParserService.parseFileToObject(FilePathConfig.getFileName());
            cityDao.getParsedCityList().forEach(System.out::println);
            System.out.println("To go to main menu press 9");
            Scanner bridgeScanner = new Scanner(System.in);
            int bridge = bridgeScanner.nextInt();

            if (bridge != 9) {
                System.out.println("To go to main menu press 9");
            } else {
                clearScreen();
                System.out.println("To operate the cities data use menu below, please");
                System.out.println("Press 1 to sort file by city names");
                System.out.println("Press 2 to sort file by city names and districts");
                System.out.println("Press 3 to view city with the largest population");
                System.out.println("Press 4 to sort regions by number of cities");

                Scanner menuScanner = new Scanner(System.in);
                int menu = menuScanner.nextInt();
                if (menu == 1) {
                    CitySorterService citySorterService = new CitySorterService(cityDao);
                    citySorterService.cityNameSort().forEach(System.out::println);
                }
                if (menu == 2) {
                    CitySorterService citySorterService = new CitySorterService(cityDao);
                    citySorterService.cityAndDistrictSort().forEach(System.out::println);
                }
                if (menu == 3) {
                    CitySorterService citySorterService = new CitySorterService(cityDao);
                    System.out.println(citySorterService.largePopulationCity());
                }
                if (menu == 4) {
                    CitySorterService citySorterService = new CitySorterService(cityDao);
                    System.out.println(citySorterService.cityNumberByRegions());
                }
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
