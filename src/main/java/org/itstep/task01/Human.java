package org.itstep.task01;

import java.text.DateFormat;

public class Human {
    private String fullName;
    private String dateOfBirth;
    private String phone;
    private String city;
    private String country;
    private String address;

    public String getFullName() {
        return fullName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return fullName + dateOfBirth  + phone +city +  country + address ;
    }
    public Human(String fullName,String dateOfBirth,String phone,String city,String country,String address){
        this.fullName = fullName;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
    public Human(){
        this.country = "Ukraine";
    }
}
