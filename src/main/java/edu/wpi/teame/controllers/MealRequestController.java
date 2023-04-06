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
  @FXML MFXTextField assignStaff;
  @FXML MFXButton clearForm;

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
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomNumber", roomNumber.getText());
    requestData.put("deliveryTime", deliveryTime.getText());
    requestData.put("mainCourse", mainCourseChoice.getText());
    requestData.put("sideCourse", sideCourseChoice.getText());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData mealRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.MEALDELIVERY,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignStaff.getText());

    // Return to home screen
    Navigation.navigate(Screen.HOME);

    DatabaseController db = new DatabaseController();
    DatabaseServiceController dbsc = new DatabaseServiceController(db);
    dbsc.addServiceRequestToDatabase(mealRequestData);

    return mealRequestData;
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  // Clears the current service request fields
  public void clearForm() {
    recipientName.clear();
    roomNumber.clear();
    deliveryTime.clear();
    mainCourseChoice.clear();
    sideCourseChoice.clear();
    notes.clear();
    assignStaff.clear();
  }
}
