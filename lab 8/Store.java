// Store.java
package PropertyReserve;


import java.util.ArrayList;

public class Store extends Property {
    private boolean hasFireExit;


   

    public Store(String id, String description, String location, int floorSpace, double rent, boolean hasFireExit) {
        super(id, description, location, "Store",  floorSpace, rent);
        this.hasFireExit = hasFireExit;
        this.isAvailable = true; // Assuming isAvailable is a field in Property class
        this.rentedBy = null; // Assuming rentedBy is a field in Property class
    }




    @Override
    public double getSecurityDeposit() {
        return getRent() * 6;
    }
}