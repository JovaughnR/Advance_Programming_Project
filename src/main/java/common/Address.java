package common;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 2L;
    public int streetNumber;
    public String streetName;
    public String city;
    public String parish_state;
    public String country;
    public String zipCode;

    public Address() {
    }

    public Address(String name, int num, String city, String parish_state, String country, String zipCode) {
        setStreetName(name);
        setStreetNumber(num);
        setCountry(country);
        setCity(city);
        setParishState(parish_state);
        setZipCode(zipCode);
    }

    public void setStreetNumber(int number) {
        streetNumber = number;
    }

    public void setStreetName(String name) {
        streetName = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zip) {
        this.zipCode = zip;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String get_parish_state() {
        return parish_state;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return streetNumber + " " + streetName + " " + city + " " + parish_state + " " + zipCode + " " + country;
    }

    public void setParishState(String text) {
        this.parish_state = text;
    }
}
