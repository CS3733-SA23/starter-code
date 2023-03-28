package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class FlowerRequestController {

  ObservableList<String> flowerChoices =
      FXCollections.observableArrayList("Tiger Lilies", "Roses", "Tulips", "Daisies");

  ObservableList<String> flowerNum = FXCollections.observableArrayList("1", "2", "6", "8", "12");

  @FXML MFXButton cancelButton;

  @FXML MFXButton submitButton;

  @FXML MFXComboBox flowerChoice;

  @FXML MFXComboBox numOfFlowers;

  @FXML
  public void initialize() {
    flowerChoice.setItems(flowerChoices);
    numOfFlowers.setItems(flowerNum);
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    submitButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
