package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingController implements IPageController {

  @FXML MFXFilterComboBox<String> startSelection;
  @FXML MFXFilterComboBox<String> endSelection;
  @FXML Text directions;
  @FXML MFXButton submitButton;

  @Override
  public void initialize() {
    // ArrayList<String> startOptions = new ArrayList<>();
    startSelection.setItems(FXCollections.observableArrayList("Node1", "Node2", "Node3"));
    endSelection.setItems(FXCollections.observableArrayList("Node4", "Node5", "Node6"));
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }

  public void submit() {
    directions.setText("submitted");
    System.out.println("bruh"); // setContentText("submitted");
  }
}
