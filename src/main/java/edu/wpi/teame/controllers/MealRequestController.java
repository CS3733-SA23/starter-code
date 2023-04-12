package edu.wpi.teame.controllers;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.ServiceRequestData;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

public class MealRequestController implements IRequestController {
  @FXML MFXButton returnButtonMealRequest;
  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField notes;
  @FXML MFXTextField recipientName;
  @FXML SearchableComboBox<String> roomName;
  @FXML SearchableComboBox<String> deliveryTime;
  @FXML SearchableComboBox<String> mainCourseChoice;
  @FXML SearchableComboBox<String> sideCourseChoice;
  @FXML MFXTextField assignedStaff;
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
    Stream<LocationName> locationStream = SQLRepo.INSTANCE.getLocationList().stream();
    ObservableList<String> names =
        FXCollections.observableArrayList(
            locationStream
                .map(
                    (locationName) -> {
                      return locationName.getLongName();
                    })
                .toList());
    roomName.setItems(names);
    mainCourseChoice.setItems(mainCourses);
    sideCourseChoice.setItems(sideCourses);
    deliveryTime.setItems(deliveryTimes);
    cancelButton.setOnMouseClicked(event -> cancelRequest());
    submitButton.setOnMouseClicked(event -> sendRequest());
    clearForm.setOnMouseClicked(event -> clearForm());
  }

  public ServiceRequestData sendRequest() {

    // Create the json to store the values
    JSONObject requestData = new JSONObject();
    requestData.put("recipientName", recipientName.getText());
    requestData.put("roomName", roomName.getValue());
    requestData.put("deliveryTime", deliveryTime.getValue());
    requestData.put("mainCourse", mainCourseChoice.getValue());
    requestData.put("sideCourse", sideCourseChoice.getValue());
    requestData.put("notes", notes.getText());

    // Create the service request data
    ServiceRequestData mealRequestData =
        new ServiceRequestData(
            ServiceRequestData.RequestType.MEALDELIVERY,
            requestData,
            ServiceRequestData.Status.PENDING,
            assignedStaff.getText());

    // Return to home screen
    Navigation.navigate(Screen.HOME);

    SQLRepo.INSTANCE.addServiceRequest(mealRequestData);

    return mealRequestData;
  }

  public void cancelRequest() {
    Navigation.navigate(Screen.HOME);
  }

  // Clears the current service request fields
  public void clearForm() {
    recipientName.clear();
    roomName.setValue(null);
    deliveryTime.setValue(null);
    mainCourseChoice.setValue(null);
    sideCourseChoice.setValue(null);
    notes.clear();
    assignedStaff.clear();
  }
}
