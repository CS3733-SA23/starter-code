package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DatabaseMapViewController {

  @FXML AnchorPane mapPane;

  @FXML ImageView imageView;

  // Sidebar Elements
  @FXML VBox sidebar;
  @FXML TextField locationField;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML MFXButton confirmButton;
  @FXML MFXButton cancelButton; // clicking will revert changes and close the sidebar
  @FXML MFXButton refreshButton;

  MapUtilities util;
  // SQLRepo dB = SQLRepo.INSTANCE;

  @FXML
  public void initialize() {
    // editableNode(new HospitalNode());
    util = new MapUtilities(mapPane);
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    //    mapPane.setMinWidth(600);
    //    mapPane.setMaxWidth(400);
    //    System.out.println("mapPane" + mapPane.getWidth());
    refreshButton.setOnMouseClicked(event -> loadFloorNodes(Floor.LOWER_ONE));
    sidebar.setVisible(false);

    // Sidebar functions
    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            sidebar.setVisible(false);
          }
        });

    //    loadFloorNodes(Floor.LOWER_ONE);
  }

  private void editableNode(HospitalNode node) {
    Circle nodePoint = util.drawHospitalNode(node);
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
    locationField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));
    int originalX = node.getXCoord();
    int originalY = node.getYCoord();
    xField.setText(node.getXCoord() + "");
    yField.setText(node.getYCoord() + "");
    nodePoint.setFill(Color.RED);

    confirmButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            updateCoords(node, xField.getText(), yField.getText(), nodePoint);
          }
        });

    xField.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            updateImage(
                nodePoint,
                locationField.getText(),
                xField.getText(),
                yField.getText(),
                originalX,
                originalY);
          }
        });

    yField.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            updateImage(
                nodePoint,
                locationField.getText(),
                xField.getText(),
                yField.getText(),
                originalX,
                originalY);
          }
        });

    locationField.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            updateImage(
                nodePoint,
                locationField.getText(),
                xField.getText(),
                yField.getText(),
                originalX,
                originalY);
          }
        });

    // cancel reset the translation
    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            nodePoint.setTranslateX(0);
            nodePoint.setTranslateY(0);
            nodePoint.setFill(Color.BLACK);
            sidebar.setVisible(false);
          }
        });

    System.out.println(node);
  }

  public void loadFloorNodes(Floor floor) {
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(floor);
    for (HospitalNode node : nodes) {
      editableNode(node);
    }
  }

  private void updateCoords(HospitalNode node, String x, String y, Circle nodePoint) {
    try {
      Integer.parseInt(x);
      Integer.parseInt(y);
      SQLRepo.INSTANCE.updateNode(node, "xcoord", x);
      SQLRepo.INSTANCE.updateNode(node, "ycoord", y);
      // update the move name

      // get rid of the circle associated with the node
      mapPane.getChildren().remove(nodePoint);
      // re-make the damn circle and node
      editableNode(node);

    } catch (NumberFormatException e) {
      // do nothing or create a popup
    }
  }

  private void updateImage(
      Circle nodePoint, String name, String x, String y, int originalX, int originalY) {
    try {
      int newX = Integer.parseInt(x);
      int newY = Integer.parseInt(y);
      nodePoint.setTranslateX(newX - originalX);
      nodePoint.setTranslateY(newY - originalY);
      // something to update the label

    } catch (NumberFormatException e) {
      // do nothing or create a popup
    }
  }
}
