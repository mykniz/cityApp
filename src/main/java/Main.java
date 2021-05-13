import config.FilePathConfig;
import dao.CityDao;
import service.CityParserService;
import service.CitySorterService;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        CityDao cityDao = new CityDao();

        CityParserService cityParserService = new CityParserService(cityDao);
        CitySorterService citySorterService = new CitySorterService(cityDao);

        System.out.println("************* MODULE 1 *************");
        cityParserService.parseFileToObject(FilePathConfig.getFileName()).getParsedCityList().forEach(System.out::println);

        System.out.println("                                    ");
        System.out.println("************* MODULE 2.1 *************");
        citySorterService.cityNameSort().forEach(System.out::println);

        System.out.println("                                    ");
        System.out.println("************* MODULE 2.2 *************");
        citySorterService.cityAndDistrictSort().forEach(System.out::println);

        System.out.println("                                    ");
        System.out.println("************* MODULE 3 *************");
        System.out.println(citySorterService.largePopulationCity());

        System.out.println("                                    ");
        System.out.println("************* MODULE 4 *************");
        System.out.println(citySorterService.cityNumberByRegions());
    }
}



