package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.fields.FurnitureFields;
import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class FurnitureController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField nameField;
  @FXML MFXTextField locationField;
  @FXML MFXTextField notesField;
  @FXML ChoiceBox furnitureTypeBox;

  private FurnitureFields furnitureFields;

  ObservableList<String> furnitureTypeList =
      FXCollections.observableArrayList("Desk", "Sofa", "Wardrobe");

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    clearButton.setOnMouseClicked(event -> clear());
    submitButton.setOnMouseClicked(event -> submit());

    furnitureTypeBox.setValue("Select Furniture");
    furnitureTypeBox.setItems(furnitureTypeList);
  }

  @FXML
  public void clear() {
    nameField.clear();
    locationField.clear();
    notesField.clear();

    furnitureTypeBox.setValue("Select Furniture");
  }

  @FXML
  public void submit() {
    String furnitureType = furnitureTypeBox.getValue().toString();

    if (furnitureType == "Select Furniture") {
      furnitureType = "";
    }

    furnitureFields =
        new FurnitureFields(
            nameField.getText(), locationField.getText(), notesField.getText(), furnitureType);

    System.out.println(
        furnitureFields.getName()
            + "\n"
            + furnitureFields.getLocation()
            + "\n"
            + furnitureFields.getNotes()
            + "\n"
            + furnitureFields.getFurniture());

    Navigation.navigate(Screen.HOME);
  }
}
