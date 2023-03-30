
package edu.wpi.teame.controllers;

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

  ObservableList<String> flowerNum = FXCollections.observableArrayList("1", "2", "6", "8", "12");

  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField recipientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXComboBox flowerChoice;
  @FXML MFXComboBox numOfFlowers;
  @FXML MFXTextField notes;

  @FXML
  public void initialize() {
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    submitButton.setOnMouseClicked(event -> sendRequest());
  }

  public ServiceRequestData sendRequest() {
    // Get the selected/input values
    String selFlowers = flowerChoice.getText();
    String selNumOfFlowers = numOfFlowers.getText();
    String inputName = recipientName.getText();
    String inputRoom = roomNumber.getText();
    String inputNotes = notes.getText();

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("flowers", selFlowers);
    requestData.put("numFlowers", selNumOfFlowers);
    requestData.put("recipient", inputName);
    requestData.put("room", inputRoom);
    requestData.put("notes", inputNotes);
    // System.out.println(requestData);

    // Create the service request data
    ServiceRequestData flowerRequestData =
        new ServiceRequestData(ServiceRequestData.RequestType.FLOWERDELIVERY, requestData);
    Navigation.navigate(Screen.HOME);
    System.out.print(
        "\nDelivery Type: "
            + flowerRequestData.requestType
            + "\nRequest Data: "
            + flowerRequestData.requestData);
    return flowerRequestData;
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }
}
