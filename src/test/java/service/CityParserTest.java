package service;

import dao.CityDao;
import entity.City;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CityParserTest {

    private final String goodPath = "cities.txt";
    private final String pathBadFile = "CitiesBadData.txt";
    private CityDao cityDao, cityDaoForTest;
    private CityParserService cityParserService;
    private CitySorterService citySorterService;

    @BeforeAll
    public void setupAll() {
        System.out.println("Tests are starting...");
    }

    @BeforeEach
    public void setup() throws FileNotFoundException {
        System.out.println("Instantiating scanning file...");

        cityParserService = new CityParserService(new CityDao());
        cityDao = cityParserService.parseFileToObject(goodPath);
        citySorterService = new CitySorterService(cityDao);
        cityDaoForTest = new CityDao();
    }

    @Test
    @DisplayName("Testing the path and origin file")
    void parseFileToObject() throws FileNotFoundException {

        City city1 = new City("Адыгейск", "Адыгея", "Южный", 12248, "1973");
        City city2 = new City("Майкоп", "Адыгея", "Южный", 144246, "1857");
        City city3 = new City("Горно-Алтайск", "Алтай", "Сибирский", 56928, "1830");

        City[] cityArrForTests = {city1, city2, city3};

        for (int i = 0; i < cityArrForTests.length; i++) {
            cityDaoForTest.getParsedCityList().add(cityArrForTests[i]);
        }

        assertEquals(cityDao, cityDaoForTest);

        assertThrows(FileNotFoundException.class, () -> new CityParserService(cityDao).parseFileToObject(""));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new CityParserService(cityDao).parseFileToObject(pathBadFile));
    }


    @Test
    @DisplayName("Testing the city name sorting")
    void cityNameSort() throws FileNotFoundException {

        City city1 = new City("Адыгейск", "Адыгея", "Южный", 12248, "1973");
        City city2 = new City("Горно-Алтайск", "Алтай", "Сибирский", 56928, "1830");
        City city3 = new City("Майкоп", "Адыгея", "Южный", 144246, "1857");

        City[] cityArrForTests = {city1, city2, city3};

        for (int i = 0; i < cityArrForTests.length; i++) {
            cityDaoForTest.getParsedCityList().add(cityArrForTests[i]);
        }

        assertEquals(citySorterService.cityNameSort(), cityDaoForTest.getParsedCityList());

    }

    @Test
    @DisplayName("Testing the city name and disctrict sorting")
    void cityAndDistrictSort() throws FileNotFoundException {

        City city1 = new City("Горно-Алтайск", "Алтай", "Сибирский", 56928, "1830");
        City city2 = new City("Адыгейск", "Адыгея", "Южный", 12248, "1973");
        City city3 = new City("Майкоп", "Адыгея", "Южный", 144246, "1857");

        City[] cityArrForTests = {city1, city2, city3};

        for (int i = 0; i < cityArrForTests.length; i++) {
            cityDaoForTest.getParsedCityList().add(cityArrForTests[i]);
        }

        assertEquals(citySorterService.cityAndDistrictSort(), cityDaoForTest.getParsedCityList());

    }

    @Test
    @DisplayName("Testing the large population city search")
    void largePopulationCity() {
        String result = "[1] = 144246";
        assertEquals(citySorterService.largePopulationCity(), result);
    }

    @Test
    @DisplayName("Testing the cities number by regions sorting")

    void cityNumberByRegions() throws FileNotFoundException {
        String result = "Южный - 2" + "\n" + "Сибирский - 1";
        assertEquals(citySorterService.cityNumberByRegions(), result);
    }


    @AfterEach
    public void tearDown() {
        System.out.println("Test completed successfully" + "\n");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("All tests have been completed successfully");
    }
}