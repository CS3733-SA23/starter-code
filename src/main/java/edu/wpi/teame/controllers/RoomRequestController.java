package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

public class RoomRequestController {
  ObservableList<String> times =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  @FXML MFXTextField recipientName;
  @FXML SearchableComboBox<String> roomName;
  @FXML SearchableComboBox<String> bookingTime;
  @FXML MFXDatePicker date;
  @FXML MFXTextField notes;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;
  @FXML MFXTextField assignedStaff;
  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    // Add the items to the combo boxes
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
    bookingTime.setItems(times);
    // Initialize the buttons

    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("deliveryTime", bookingTime.getValue());
    requestData.put("bookingDate", date.getText());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomNumber", roomName.getValue());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData flowerRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.CONFERENCEROOM,
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
    bookingTime.setValue(null);
    recipientName.clear();
    roomName.setValue(null);
    notes.clear();
    assignedStaff.clear();
    date.clear();
  }
}
