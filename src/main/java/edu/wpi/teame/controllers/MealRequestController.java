package edu.wpi.teame.controllers;

import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import org.json.JSONObject;

public class MealRequestController implements IRequestController {
  @FXML MFXButton backButton;
  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField deliveryTime;
  @FXML MFXTextField foodSelection;
  @FXML MFXTextField notes;
  @FXML MFXTextField receiverName;
  @FXML MFXTextField roomNumber;
  @FXML MFXTextField sideSelection;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    submitButton.setOnMouseClicked(event -> sendRequest());
  }

  public ServiceRequestData sendRequest() {
    // Get the selected/input values
    String selCourse = foodSelection.getText();
    String selSide = sideSelection.getText();
    String inputName = receiverName.getText();
    String inputDelivery = deliveryTime.getText();
    String inputRoom = roomNumber.getText();
    String inputNotes = notes.getText();

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("course", selCourse);
    requestData.put("side", selSide);
    requestData.put("recipient", inputName);
    requestData.put("deliveryTime", inputDelivery);
    requestData.put("room", inputRoom);
    requestData.put("notes", inputNotes);

    // Create the service request data
    ServiceRequestData mealRequestData =
        new ServiceRequestData(ServiceRequestData.RequestType.MEALDELIVERY, requestData);
    Navigation.navigate(Screen.HOME);
    System.out.print(
        "\nDelivery Type: "
            + mealRequestData.getRequestType()
            + "\nRequest Data: "
            + mealRequestData.getRequestData());
    return mealRequestData;
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }
}
