package PropertyReserve;

import java.util.ArrayList;

public class OfficeSpace extends Property {
    private int minLeaseTime;

    public OfficeSpace(String id, String description, String location, 
                       int floorSpace, double rent, int minLeaseTime) {
        super(id, description, location, "OfficeSpace", floorSpace, rent);
        this.minLeaseTime = minLeaseTime;
    }

    @Override
    public double getSecurityDeposit() {
        return getRent() * 6;
    }
}