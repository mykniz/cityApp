package service;

import dao.CityDao;
import entity.City;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CitySorterService {

    private final CityDao cityDao;

    public CitySorterService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> cityNameSort() {
        return cityDao.cityNameSort();
    }

    public List<City> cityAndDistrictSort() {
        return cityDao.cityAndDistrictSort();
    }

    public String largePopulationCity() {
        return cityDao.largePopulationCity();
    }

    public String cityNumberByRegions() {
        return cityDao.cityNumberByRegions();
    }
}
