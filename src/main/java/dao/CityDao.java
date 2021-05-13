package dao;

import entity.City;

import java.util.*;
import java.util.stream.Collectors;

public class CityDao {

    private final List<City> parsedCityList = new ArrayList<>();

    public CityDao() {
    }

    public void addCity(City city) {
        parsedCityList.add(city);
    }

    public List<City> getParsedCityList() {
        return parsedCityList;
    }

    public List<City> cityNameSort() {
        return parsedCityList.stream()
                .sorted(Comparator.comparing(City::getName))
                .collect(Collectors.toList());
    }

    public List<City> cityAndDistrictSort() {
        return parsedCityList.stream()
                .sorted(Comparator.comparing(City::getName))
                .sorted(Comparator.comparing(City::getDistrict))
                .collect(Collectors.toList());
    }

    public String largePopulationCity() {

        City[] arr = parsedCityList.toArray(new City[0]);

        int index = 0;
        int population = 0;
        for (int i = 0; i < arr.length; i++) {

            if (arr[i].getPopulation() > population) {
                index = i;
                population = arr[i].getPopulation();
            }
        }
        return "[" + index + "]" + " = " + population;
    }

    public String cityNumberByRegions() {

        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Long> resultMap = parsedCityList.stream()
                .collect(Collectors
                        .groupingBy(City::getDistrict, Collectors.counting()));

        for (String key : resultMap.keySet()) {
            stringBuilder.append(key + " - " + resultMap.get(key) + "\n");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityDao cityDao = (CityDao) o;
        return parsedCityList.equals(cityDao.parsedCityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parsedCityList);
    }
}
