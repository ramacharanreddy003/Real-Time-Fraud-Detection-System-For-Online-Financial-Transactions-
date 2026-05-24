package model;

public class LocationInfo {

    private String country;
    private String state;
    private String city;

    public LocationInfo(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return city + ", " + state + ", " + country;
    }
}
