package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.DatabaseController;
import edu.wpi.teame.Database.DatabaseServiceController;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.json.JSONObject;

public class OfficeSuppliesController implements IRequestController {

  @FXML MFXButton returnButtonOfficeSuppliesRequest;
  @FXML MFXButton submitButton;
  @FXML MFXButton cancelButton;
  @FXML MFXButton clearForm;
  @FXML MFXTextField staffName;
  @FXML MFXTextField officeNumber;
  @FXML MFXTextField notes;
  @FXML MFXComboBox<String> deliveryTime;
  @FXML MFXComboBox<String> officeSupplyType;
  @FXML MFXTextField quantityOfSupplies;
  @FXML MFXTextField assignedStaff;

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");
  ObservableList<String> officeSupplies =
      FXCollections.observableArrayList(
          "pencils", "pens", "white-out", "tape", "ruler", "hole puncher", "sharpener", "charger");

  @FXML
  public void initialize() {
    deliveryTime.setItems(deliveryTimes);
    officeSupplyType.setItems(officeSupplies);
    submitButton.setOnMouseClicked(event -> sendRequest());
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  private void clearForm() {
    staffName.clear();
    officeNumber.clear();
    notes.clear();
    deliveryTime.clear();
    officeSupplyType.clear();
    quantityOfSupplies.clear();
    assignedStaff.clear();
  }

  @Override
  public ServiceRequestData sendRequest() {
    JSONObject requestData = new JSONObject();
    requestData.put("staffName", staffName.getText());
    requestData.put("officeNumber", officeNumber.getText());
    requestData.put("deliveryTime", deliveryTime.getText());
    requestData.put("supplyType", officeSupplyType.getText());
    requestData.put("numOfSupplies", quantityOfSupplies.getText());
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
