package com.pivotallabs.pivottexts.pivotsconnector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pivot {

    @JsonProperty("id") private int id;
    @JsonProperty("phone") private String phone;
    @JsonProperty("location_name") private String locationName;
    @JsonProperty("first_name") private String firstName;
    @JsonProperty("last_name") private String lastName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}
