package edu.wpi.teamR.controllers;

import edu.wpi.teamR.fields.MealFields;
import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class MealRequestController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton resetButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField nameField;
  @FXML MFXTextField locationField;
  @FXML MFXTextField staffMemberField;
  @FXML MFXTextField notesField;
  @FXML ChoiceBox mealTypeBox;

  private MealFields mealField;

  ObservableList<String> mealTypeList =
      FXCollections.observableArrayList("Chicken", "Beef", "Fish");

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    resetButton.setOnMouseClicked(event -> clear());
    submitButton.setOnMouseClicked(event -> submit());
    mealTypeBox.setValue("Select Meal");
    mealTypeBox.setItems(mealTypeList);
  }

  @FXML
  public void submit() {
    String mealType = mealTypeBox.getValue().toString();
    if (mealType == "Select Meal") {
      mealType = "";
    }
    mealField =
        new MealFields(
            nameField.getText(),
            locationField.getText(),
            staffMemberField.getText(),
            notesField.getText(),
            mealType);
    System.out.println(
        mealField.getName()
            + " "
            + mealField.getLocation()
            + " "
            + mealField.getStaffMember()
            + " "
            + mealField.getNotes()
            + " "
            + mealField.getMeal());
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  public void clear() {
    nameField.clear();
    locationField.clear();
    staffMemberField.clear();
    notesField.clear();
    mealTypeBox.setValue("Select Meal");
  }
}
