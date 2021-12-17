package org.itstep.task03;

import org.itstep.task02.City;

import java.util.ArrayList;
import java.util.Arrays;

public class Country {

    private String name;
    private String continent;
    private String code;
    private City capital;
    private ArrayList<City> cities = new ArrayList<City>();

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public City getCapital() {
        return capital;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public String getContinent() {
        return continent;
    }
    public int getInhabitants() {
        int sum = 0;
        for (City city:this.cities
             ) {
            sum+=(int)city.getInhabitants();
        }

        return sum;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
    public void addCity(City city) {
    cities.add(city);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", code='" + code + '\'' +
                ", capital=" + capital +
                ", cities=" + cities +
                '}';
    }

    public Country(String name, String continent, String code, City capital) {
        this.name = name;
        this.continent = continent;
        this.code = code;
        this.capital = capital;
    }
    public  Country(){
        this.name = "Ukraine";
    }
}
