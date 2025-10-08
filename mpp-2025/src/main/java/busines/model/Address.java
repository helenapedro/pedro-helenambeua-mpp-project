package busines.model;

import java.util.Objects;

public class Address {
    private int addressId;
    private String city, state, zipcode;

    public Address() {}

    public Address(int addressId, String city, String state, String zipcode) {
        this.addressId = addressId;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return city + ", " + state + " " + (zipcode != null ? zipcode : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return addressId == address.addressId;
    }

    @Override
    public int hashCode() { return Objects.hash(addressId); }

    public int getAddressId() { return addressId; }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
}