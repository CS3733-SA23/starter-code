package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;

public class DatabaseViewController {

  @FXML MFXButton backButton;
  @FXML TableView<MapInitializer> dataTable;

  @FXML TableColumn<MapInitializer, String> nodeIDCol;

  @FXML TableColumn<MapInitializer, String> nameCol;

  @FXML TableColumn<MapInitializer, String> dateCol;

  @FXML
  public void initialize() {
    // load the database into the table
    nodeIDCol.setCellValueFactory(new PropertyValueFactory<MapInitializer, String>("nodeID"));
    nameCol.setCellValueFactory(new PropertyValueFactory<MapInitializer, String>("longName"));
    dateCol.setCellValueFactory(new PropertyValueFactory<MapInitializer, String>("date"));

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    List itemList = new ArrayList<>(); // REPLACE WITH THE METHOD CALL

    dataTable.setItems();

    dataTable.setEditable(true);
  }
}
