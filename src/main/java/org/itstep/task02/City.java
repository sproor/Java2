package org.itstep.task02;

public class City {
    private String name;
    private String region;
    private String country;
    private int inhabitants;
    private String index;
    private String code;

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public String getIndex() {
        return index;
    }

    public int getInhabitants() {
        return inhabitants;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setInhabitants(int inhabitants) {
        this.inhabitants = inhabitants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", inhabitants='" + inhabitants + '\'' +
                ", index='" + index + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public City(String name, String region, String country, int inhabitants, String index, String code) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.inhabitants = inhabitants;
        this.index = index;
        this.code = code;
    }

    public City() {
        this.country = "Ukraine";
    }
}
