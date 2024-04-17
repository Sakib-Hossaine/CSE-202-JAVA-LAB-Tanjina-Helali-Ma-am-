package PropertyReserve;

import java.util.ArrayList;

public abstract class Property {
    private String id;
    private String description;
    private String location;
    private String category;

//(id1, description,location, category, floorspace, rent, noOfBedRooms, noOfWashRooms, hasGenerator);    private ArrayList<String> facilities;
    private int floorSpace;
    private double rent;
    protected boolean isAvailable;
    protected String rentedBy;

    public Property(String id, String description, String location, String category, double floorspace2, double rent) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.category = category;
        this.floorSpace = floorSpace;
        this.rent = rent;
        this.isAvailable = true;
        this.rentedBy = null;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public double getFloorSpace() {
        return floorSpace;
    }

    public double getRent() {
        return rent;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getRentedBy() {
        return rentedBy;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setRentedBy(String rentedBy) {
        this.rentedBy = rentedBy;
    }

    public void rentProperty(double rent, String rentedBy) {
        setAvailable(false);
        setRent(rent);
        setRentedBy(rentedBy);
    }

    public double giveMonthlyPayment() {
        return rent;
    }

    public void leaseOver() {
        setAvailable(true);
        setRentedBy(null);
    }

    public abstract double getSecurityDeposit();

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", floorSpace=" + floorSpace +
                ", rent=" + rent +
                ", isAvailable=" + isAvailable +
                ", rentedBy='" + rentedBy + '\'' +
                '}';
    }
}