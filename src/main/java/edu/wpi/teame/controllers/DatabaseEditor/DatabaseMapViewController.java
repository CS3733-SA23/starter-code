package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.utilities.MapUtilities;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class DatabaseMapViewController {

  @FXML AnchorPane mapPane;
  MapUtilities util = new MapUtilities();

  @FXML
  public void initialize() {
    editableNode(new HospitalNode());
  }

  private void editableNode(HospitalNode node) {
    Circle nodePoint = util.drawHospitalNode(node, mapPane);
    nodePoint.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            displayMetadata(node);
          }
        });
  }

  private void displayMetadata(HospitalNode node) {
    // activate side panel with correct data
    System.out.println(node);
  }
}
