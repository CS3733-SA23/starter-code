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
  @FXML MFXComboBox deliveryTime;
  @FXML MFXComboBox flowerChoice;
  @FXML MFXComboBox numOfFlowers;
  @FXML MFXTextField notes;

  @FXML
  public void initialize() {
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    deliveryTime.setItems(deliveryTimes);
    returnButtonFlowerRequest.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    flowerRequestSubmit.setOnMouseClicked(event -> sendRequest());
  }

  public ServiceRequestData sendRequest() {
    // Get the selected/input values
    String selFlowers = flowerChoice.getText();
    String selNumOfFlowers = numOfFlowers.getText();
    String selDeliveryTime = deliveryTime.getText();
    String inputName = recipientName.getText();
    String inputRoom = roomNumber.getText();
    String inputNotes = notes.getText();

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("flowers", selFlowers);
    requestData.put("numFlowers", selNumOfFlowers);
    requestData.put("deliveryTime", selDeliveryTime);
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
