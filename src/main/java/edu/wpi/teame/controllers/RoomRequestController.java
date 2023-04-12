package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.DatabaseController;
import edu.wpi.teame.Database.DatabaseServiceController;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.JSONObject;

public class RoomRequestController {
  ObservableList<String> times =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  @FXML MFXTextField recipientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXComboBox<String> bookingTime;
  @FXML MFXDatePicker date;
  @FXML MFXTextField notes;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;
  @FXML MFXTextField assignedStaff;
  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    // Add the items to the combo boxes

    bookingTime.setItems(times);
    // Initialize the buttons

    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("deliveryTime", bookingTime.getText());
    requestData.put("bookingDate", date.getText());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomNumber", roomNumber.getText());
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

    DatabaseController db = DatabaseController.INSTANCE;
    DatabaseServiceController dbsc = new DatabaseServiceController(db);

    dbsc.addServiceRequestToDatabase(flowerRequestData);
    return flowerRequestData;
  }

  // Cancels the current service request
  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  // Clears the current service request fields
  public void clearForm() {
    bookingTime.clear();
    recipientName.clear();
    roomNumber.clear();
    notes.clear();
    assignedStaff.clear();
    date.clear();
  }
}
