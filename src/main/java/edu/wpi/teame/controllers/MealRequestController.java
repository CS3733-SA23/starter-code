package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class MealRequestController implements IRequestController {
  @FXML MFXButton returnButtonMealRequest;
  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField notes;
  @FXML MFXTextField recipientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXComboBox<String> deliveryTime;
  @FXML MFXComboBox<String> mainCourseChoice;
  @FXML MFXComboBox<String> sideCourseChoice;

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

    mouseSetup(returnButtonMealRequest);
    mouseSetup(cancelButton);
    mouseSetup(submitButton);
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
    //    ServiceRequestData mealRequestData =
    //        new ServiceRequestData(ServiceRequestData.RequestType.MEALDELIVERY, requestData);
    Navigation.navigate(Screen.HOME);
    //    System.out.print(
    //        "\nDelivery Type: "
    //            + mealRequestData.getRequestType()
    //            + "\nRequest Data: "
    //            + mealRequestData.getRequestData());

    return new ServiceRequestData(
        ServiceRequestData.RequestType.MEALDELIVERY,
        new JSONObject(),
        ServiceRequestData.Status.PENDING,
        "Not Functioning");
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }
}
