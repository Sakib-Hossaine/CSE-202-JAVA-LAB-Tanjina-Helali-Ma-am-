// Apartment.java
package PropertyReserve;

import java.util.ArrayList;

public class Apartment extends Property {
    private int noOfBedRoom;
    private int noOfWashRoom;
    private boolean hasGenerator;

    public Apartment(String id, String description, String location,
                     double floorspace2, double rent2, int floorspace, double rent, boolean hasGenerator) {
        super(id, description, location, "Apartment",  floorspace2, rent2);
        this.noOfBedRoom = (int) floorspace;
        this.noOfWashRoom = (int) rent;
        this.hasGenerator = hasGenerator;
    }

   
    @Override

    public double getSecurityDeposit() {
        return getRent() * 3;
    }

    public int getNoOfBedRoom() {
        return this.noOfBedRoom;
    }

    public int getNoOfWashRoom() {
        return this.noOfWashRoom;
    }
}