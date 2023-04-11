package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class DatabaseMapViewController {

  @FXML AnchorPane mapPane;

  // Sidebar Elements
  @FXML VBox sidebar;
  @FXML TextField locationField;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML MFXButton confirmButton;
  @FXML MFXButton cancelButton; // clicking will revert changes and close the sidebar

  MapUtilities util = new MapUtilities();
  SQLRepo dB = SQLRepo.INSTANCE;

  @FXML
  public void initialize() {
    editableNode(new HospitalNode());
    sidebar.setVisible(false);

    // Sidebar functions
    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            sidebar.setVisible(false);
          }
        });
  }

  private void editableNode(HospitalNode node) {
    Circle nodePoint = util.drawHospitalNode(node, mapPane);
    nodePoint.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            displayMetadata(node, nodePoint);
          }
        });
  }

  private void displayMetadata(HospitalNode node, Circle nodePoint) {
    // activate side panel with correct data
    sidebar.setVisible(true);
    locationField.setText(node.getNodeID());
    xField.setText(node.getXCoord() + "");
    yField.setText(node.getYCoord() + "");

    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            updateNode(node, locationField.getText(), Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
        }
    });
    

    System.out.println(node);
  }

  private void loadFloorNodes() {
    // ArrayList<HospitalNode> nodes =
  }

  private void updateNode(HospitalNode node, String name, int x, int y){

  }

  private void updateImage(Circle nodePoint, String name, int x, int y){

  }
}
