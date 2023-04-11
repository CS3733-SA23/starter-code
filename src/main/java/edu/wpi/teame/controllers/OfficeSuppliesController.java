package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.DatabaseController;
import edu.wpi.teame.Database.DatabaseServiceController;
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

public class OfficeSuppliesController implements IRequestController {

  @FXML MFXButton returnButtonOfficeSuppliesRequest;
  @FXML MFXButton officeSuppliesRequestSubmit;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;
  @FXML MFXTextField staffName;
  @FXML MFXTextField officeNumber;
  @FXML MFXTextField notes;
  @FXML MFXComboBox<String> deliveryTime;
  @FXML MFXComboBox<String> blank1Choice;
  @FXML MFXComboBox<String> blank2Choice;
  @FXML MFXTextField assignedStaff;

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");
  ObservableList<String> blank1Choices =
      FXCollections.observableArrayList("stuff, more stuff, some more stuff");

  ObservableList<String> blank2Choices =
      FXCollections.observableArrayList("things, more things, some more things");

  @FXML
  public void initialize() {
    deliveryTime.setItems(deliveryTimes);
    blank1Choice.setItems(blank1Choices);
    blank2Choice.setItems(blank2Choices);
    returnButtonOfficeSuppliesRequest.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    officeSuppliesRequestSubmit.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  private void clearForm() {
    staffName.clear();
    officeNumber.clear();
    notes.clear();
    deliveryTime.clear();
    blank1Choice.clear();
    blank2Choice.clear();
    assignedStaff.clear();
  }

  @Override
  public ServiceRequestData sendRequest() {
    JSONObject requestData = new JSONObject();
    requestData.put("staffName", staffName.getText());
    requestData.put("officeNumber", officeNumber.getText());
    requestData.put("deliveryTime", deliveryTime.getText());
    requestData.put("blank1Choice", blank1Choice.getText());
    requestData.put("blank2Choice", blank2Choice.getText());
    requestData.put("notes", notes.getText());

    ServiceRequestData officeSuppliesRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.OFFICESUPPLIESDELIVERY,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignedStaff.getText());

    Navigation.navigate(Screen.HOME);

    DatabaseController db = DatabaseController.INSTANCE;
    DatabaseServiceController dbsc = new DatabaseServiceController(db);

    dbsc.addServiceRequestToDatabase(officeSuppliesRequestData);
    return officeSuppliesRequestData;
  }

  @Override
  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }
}
