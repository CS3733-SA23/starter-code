package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

public class FlowerRequestController implements IRequestController {

  ObservableList<String> flowerChoices =
      FXCollections.observableArrayList("Tiger Lilies", "Roses", "Tulips", "Daisies");

  ObservableList<String> flowerNum =
      FXCollections.observableArrayList(
          "1", "2", "Small Bouquet (6)", "Medium Bouquet (8)", "Large Bouquet (12)");

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  @FXML MFXButton returnButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField recipientName;
  @FXML SearchableComboBox roomName;
  @FXML SearchableComboBox<String> deliveryTime;
  @FXML SearchableComboBox<String> flowerChoice;
  @FXML SearchableComboBox<String> numOfFlowers;
  @FXML MFXTextField notes;
  @FXML MFXTextField assignedStaff;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;

  @FXML
  public void initialize() {
    Stream<LocationName> locationStream = SQLRepo.INSTANCE.getLocationList().stream();
    ObservableList<String> names =
        FXCollections.observableArrayList(
            locationStream
                .map(
                    (locationName) -> {
                      return locationName.getLongName();
                    })
                .toList());
    roomName.setItems(names);
    // Add the items to the combo boxes
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    deliveryTime.setItems(deliveryTimes);
    // Initialize the buttons
    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("flowerChoice", flowerChoice.getValue());
    requestData.put("numOfFlowers", numOfFlowers.getValue());
    requestData.put("deliveryTime", deliveryTime.getValue());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomName", roomName.getValue());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData flowerRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.FLOWERDELIVERY,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignedStaff.getText());

    // Return to the home screen
    Navigation.navigate(Screen.HOME);

    SQLRepo.INSTANCE.addServiceRequest(flowerRequestData);
    return flowerRequestData;
  }

  // Cancels the current service request
  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  // Clears the current service request fields
  public void clearForm() {
    flowerChoice.setValue(null);
    numOfFlowers.setValue(null);
    deliveryTime.setValue(null);
    roomName.setValue(null);
    recipientName.clear();
    notes.clear();
    assignedStaff.clear();
  }
}
