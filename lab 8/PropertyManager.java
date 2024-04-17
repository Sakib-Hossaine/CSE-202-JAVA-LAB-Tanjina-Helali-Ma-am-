package PropertyReserve;

import java.util.ArrayList;

public class PropertyManager {
    private String name;
    private ArrayList<Property> properties;

    public PropertyManager(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
    }
//(id1, description,location, category, floorspace, rent, noOfBedRooms, noOfWashRooms, hasGenerator);
    public void addProperty(String id,String description, String location,String category, double floorspace, double rent, int noOfBedRooms, int noOfWashRooms, boolean hasGenerator) {
        Apartment apartment = new Apartment(id, description, location,  floorspace, rent, noOfBedRooms, noOfWashRooms, hasGenerator);
        this.properties.add(apartment);
    }

    public void addProperty(String id, String location, int floorSpace, double rent, boolean hasFireExit) {
        Store store = new Store(id, "Store", location,  floorSpace, rent, hasFireExit);
        properties.add(store);
    }

    public void addProperty(String id, String location, int floorSpace, double rent, int minLeaseTime) {
        OfficeSpace officeSpace = new OfficeSpace(id, "OfficeSpace", location,  floorSpace, rent, minLeaseTime);
        properties.add(officeSpace);
    }

    public ArrayList<Apartment> findApartments(String location, int noOfBedRooms, int noOfWashRooms) {
        ArrayList<Apartment> foundApartments = new ArrayList<>();
        for (Property property : properties) {
            if (property instanceof Apartment) {
                Apartment apartment = (Apartment) property;
                if (apartment.getLocation().equals(location) &&
                        apartment.getNoOfBedRoom() == noOfBedRooms &&
                        apartment.getNoOfWashRoom() == noOfWashRooms) {
                    foundApartments.add(apartment);
                }
            }
        }
        return foundApartments;
    }

    public void rentProperty(String id, double rentAmount, String rentedBy) {
        for (Property property : this.properties) {
            if (property.getId().equals(id)) {
                property.setRent(rentAmount);
                property.setRentedBy(rentedBy);
                return;
            }
        }
        System.out.println("Property with id " + id + " not found.");
    }

    public void leaseOver(String id) {
        for (Property property : this.properties) {
            if (property.getId().equals(id)) {
                property.setRent(0);
                property.setRentedBy(null);
                return;
            }
        }
        System.out.println("Property with id " + id + " not found.");
    }

    public Property findProperty(String id) {
        for (Property property : this.properties) {
            if (property.getId().equals(id)) {
                return property;
            }
        }
        return null;
    }
    public void viewByCategory(String category) {
        for (Property property : this.properties) {
            if (property.getCategory().equals(category)) {
                System.out.println(property);
            }
        }
    }
    public void displayAllProperties() {
        for (Property property : this.properties) {
            System.out.println(property);
        }
    }

    // Other find methods and management methods as described in the prompt
}