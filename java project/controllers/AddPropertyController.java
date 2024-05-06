package controllers;

import application.App;
import application.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class AddPropertyController {
    // AddPropertyController class.

    @FXML
    private TextField bedroomTF, washroomTF, apartmentLocationTF, apartmentRentTF, apartmentSizeTF,
            commercialLocationTF, commercialRentTF, commercialSizeTF;
    // TextFields for various property attributes.

    @FXML
    private CheckBox hasGeneratorCheckbox, hasFireExitCheckbox;
    // CheckBoxes for property features.

    @FXML
    void addApartment(ActionEvent event) { // Method to handle addApartment action.
        String location = apartmentLocationTF.getText();
        // Get the location from the TextField.
        boolean hasGenerator = hasGeneratorCheckbox.isSelected();
        // Check if the generator checkbox is selected.
        double rent = Utils.parseDouble(apartmentRentTF.getText());
        // Parse the rent from the TextField.
        double floorSpace = Utils.parseDouble(apartmentSizeTF.getText());
        // Parse the floor space from the TextField.
        int noOfBedroom = Utils.parseInt(bedroomTF.getText());
        // Parse the number of bedrooms from the TextField.
        int noOfWashroom = Utils.parseInt(washroomTF.getText());
        // Parse the number of washrooms from the TextField.
        String id = App.propertyManager.addProperty(location, rent, floorSpace, noOfBedroom, noOfWashroom,
                hasGenerator); // Add the property and get the id.
        System.out.println("Apartment with id " + id + " added.");
        // Print the id of the added property.
        resetApartmentForm(); // Reset the form.
    }

    @FXML
    void addCommercialSpace(ActionEvent event) {
        // Method to handle addCommercialSpace action.
        String location = commercialLocationTF.getText();
        // Get the location from the TextField.
        double rent = Utils.parseDouble(commercialRentTF.getText());
        // Parse the rent from the TextField.
        double floorSpace = Utils.parseDouble(commercialSizeTF.getText());
        // Parse the floor space from the TextField.
        boolean hasFireExit = hasFireExitCheckbox.isSelected();
        // Check if the fire exit checkbox is selected.
        String id = App.propertyManager.addProperty(location, rent, floorSpace, hasFireExit);
        // Add the property and get the id.
        System.out.println("Commercial space with id " + id + " added.");
        // Print the id of the added property.
        resetCommercialSpaceForm(); // Reset the form.
    }

    void resetApartmentForm() {
        // Method to reset the apartment form.
        apartmentLocationTF.clear();
        apartmentRentTF.clear();
        apartmentSizeTF.clear();
        bedroomTF.clear();
        washroomTF.clear();
        hasGeneratorCheckbox.setSelected(false); // Uncheck the generator checkbox.
    }

    void resetCommercialSpaceForm() { // Method to reset the commercial space form.
        commercialLocationTF.clear(); // Clear the location TextField.
        commercialRentTF.clear(); // Clear the rent TextField.
        commercialSizeTF.clear(); // Clear the size TextField.
        hasFireExitCheckbox.setSelected(false); // Uncheck the fire exit checkbox.
    }

}