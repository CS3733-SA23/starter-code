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

public class MealRequestController implements IRequestController {
  @FXML MFXButton returnButtonMealRequest;
  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXComboBox deliveryTime;
  @FXML MFXTextField notes;
  @FXML MFXTextField recipientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXComboBox mainCourseChoice;
  @FXML MFXComboBox sideCourseChoice;

  ObservableList<String> deliveryTimes =
      FXCollections.observableArrayList(
          "10am - 11am", "11am - 12pm", "12pm - 1pm", "1pm - 2pm", "2pm - 3pm", "3pm - 4pm");

  ObservableList<String> mainCourses =
      FXCollections.observableArrayList(
          "Hamburger", "Cheeseburger", "Grilled Cheese", "Chicken Nuggets");
  ObservableList<String> sideCourses =
      FXCollections.observableArrayList("Fries", "Apple Slices", "Tater Tots", "Carrots");

  @FXML
  public void initialize() {
    mainCourseChoice.setItems(mainCourses);
    sideCourseChoice.setItems(sideCourses);
    deliveryTime.setItems(deliveryTimes);
    returnButtonMealRequest.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    submitButton.setOnMouseClicked(event -> sendRequest());
  }

  public ServiceRequestData sendRequest() {
    // Get the selected/input values
    String selCourse = mainCourseChoice.getText();
    String selSide = sideCourseChoice.getText();
    String inputName = recipientName.getText();
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
            + mealRequestData.requestType
            + "\nRequest Data: "
            + mealRequestData.requestData);
    return mealRequestData;
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }
}
