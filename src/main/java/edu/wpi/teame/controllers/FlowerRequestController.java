package edu.wpi.teame.controllers;

import Database.DatabaseController;
import Database.DatabaseServiceController;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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

  @FXML MFXButton returnButtonFlowerRequest;
  @FXML MFXButton flowerRequestSubmit;
  @FXML MFXTextField recipientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXComboBox<String> deliveryTime;
  @FXML MFXComboBox<String> flowerChoice;
  @FXML MFXComboBox<String> numOfFlowers;
  @FXML MFXTextField notes;
  @FXML MFXTextField assignedStaff;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;

  @FXML
  public void initialize() {
    // Add the items to the combo boxes
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    deliveryTime.setItems(deliveryTimes);
    // Initialize the buttons
    returnButtonFlowerRequest.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    flowerRequestSubmit.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("flowerChoice", flowerChoice.getText());
    requestData.put("numOfFlowers", numOfFlowers.getText());
    requestData.put("deliveryTime", deliveryTime.getText());
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomNumber", roomNumber.getText());
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

    DatabaseController db = new DatabaseController();
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
    flowerChoice.clear();
    numOfFlowers.clear();
    deliveryTime.clear();
    recipientName.clear();
    roomNumber.clear();
    notes.clear();
    assignedStaff.clear();
  }
}
