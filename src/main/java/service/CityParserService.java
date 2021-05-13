package service;

import dao.CityDao;
import entity.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CityParserService {

    private final CityDao cityDao;

    public CityParserService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public CityDao parseFileToObject(String filename) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        URL resource;
        resource = getClass().getClassLoader().getResource(filename);
        List<String[]> dataFromFile = new ArrayList<>();
        File file = new File(resource.getFile());
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String[] lines = scanner.nextLine().split(";");
            dataFromFile.add(lines);
        }
        scanner.close();

        for (String[] array : dataFromFile) {
            City city = new City(
                    array[1],
                    array[2],
                    array[3],
                    Integer.parseInt(array[4]),
                    array[5]);
            cityDao.addCity(city);
        }
        return cityDao;
    }
}