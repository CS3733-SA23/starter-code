package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DatabaseMapViewController {

  @FXML AnchorPane mapPane;

  @FXML ImageView imageView;

  // Sidebar Elements
  @FXML VBox sidebar;
  // @FXML TextField longNameField;
  // @FXML TextField shortNameField;
  @FXML ComboBox<String> longNameSelector;
  @FXML MFXButton addLocationButton;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML MFXButton confirmButton;
  @FXML MFXButton cancelButton; // clicking will revert changes and close the sidebar
  // @FXML MFXButton refreshButton;
  @FXML HBox coordFields;

  @FXML HBox nameFields;

  Floor currentFloor;
  MapUtilities util;

  HospitalNode currentNode;
  LinkedList<HospitalNode> modifiedNodes = new LinkedList<>();
  LinkedList<LocationName> modifiedLocationNames = new LinkedList<>();
  LinkedList<String> modifiedMoveLongNames = new LinkedList<>();

  @FXML
  public void initialize() {
    util = new MapUtilities(mapPane);
    currentFloor = Floor.LOWER_ONE;
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");
    // refreshButton.setOnMouseClicked(event -> loadFloorNodes(Floor.LOWER_ONE));
    sidebar.setVisible(false);

    // Sidebar functions
    cancelButton.setOnAction(event -> sidebar.setVisible(false));

    confirmButton.setOnAction(
        event -> {
          updateNodeDatabase();
        });
    updateCombo();
  }

  private void editableNode(HospitalNode node) {
    Circle nodePoint = util.drawHospitalNode(node);
    try {
      Label nodeLabel =
          util.createLabel(
              node.getXCoord(),
              node.getYCoord(),
              SQLRepo.INSTANCE.getShortNameFromNodeID(node.getNodeID()));
    } catch (SQLException e) {
      System.out.println(e);
    }
    nodePoint.setOnMouseClicked(
        event -> {
          for (Node nodeCircle : util.filterShapes(Circle.class)) {
            Circle circle = ((Circle) nodeCircle);
            circle.setFill(Color.BLACK);
          }
          nodePoint.setFill(Color.RED);

          modifiedNodes.add(node);
          displayMetadata(node, nodePoint);
          try {
            modifiedNodes.add(
                new HospitalNode(
                    currentNode.getNodeID(),
                    Integer.parseInt(xField.getText()),
                    Integer.parseInt(yField.getText()),
                    currentFloor,
                    currentNode.getBuilding()));
          } catch (RuntimeException e) {
            System.out.println(e);
          }
          System.out.println(modifiedNodes);
          currentNode = node;
        });

    nodePoint.setOnMouseDragged(
        event -> {
          ((Circle) event.getSource()).setCenterX(event.getX());
          ((Circle) event.getSource()).setCenterY(event.getY());
          updateMetadata(node, nodePoint);
        });
  }

  private void displayMetadata(HospitalNode node, Circle nodePoint) {
    // longNameField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));

    xField.setText(util.PaneXToImageX(nodePoint.getCenterX()) + "");
    yField.setText(util.PaneYToImageY(nodePoint.getCenterY()) + "");
    sidebar.setVisible(true);
    coordFields.setVisible(true);
    nameFields.setVisible(false);
  }

  private void updateMetadata(HospitalNode node, Circle nodePoint) {
    // longNameField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));

    xField.setText(util.PaneXToImageX(nodePoint.getCenterX()) + "");
    yField.setText(util.PaneYToImageY(nodePoint.getCenterY()) + "");
  }

  public void loadFloorNodes(Floor floor) {
    util.removeAll(Circle.class);
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(floor);
    for (HospitalNode node : nodes) {
      editableNode(node);
    }
  }

  private void updateNodeDatabase() {
    for (int i = 0; i < modifiedNodes.size(); i++) {
      editFromNode(modifiedNodes.get(i));
    }
    for (HospitalNode node : modifiedNodes) {
      editFromNode(node);
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

  private void updateCombo() {
    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();
    List<String> longNames = SQLRepo.INSTANCE.getLongNamesFromLocationName(locationNames);
    longNameSelector.setItems(FXCollections.observableList(longNames));
  }

  private void editFromNode(HospitalNode node) {
    SQLRepo.INSTANCE.updateNode(node, "xcoord", node.getXCoord() + "");
    SQLRepo.INSTANCE.updateNode(node, "ycoord", node.getYCoord() + "");
    //    // update the move name
    //    SQLRepo.INSTANCE.updateUsingNodeID(
    //        node.getNodeID(),
    //        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())),
    //        "longName",
    //        longName);
    //    SQLRepo.INSTANCE.updateLocation(locationName, "shortName", locationName.getShortName());
  }

  /*
  private void displayMetadata(HospitalNode node, Circle nodePoint) {
    // activate side panel with correct data
    sidebar.setVisible(true);
    locationField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));
    int originalX = node.getXCoord();
    int originalY = node.getYCoord();
    String originalName = SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID()));
    xField.setText(node.getXCoord() + "");
    yField.setText(node.getYCoord() + "");
    nodePoint.setFill(Color.RED);

    confirmButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            updateCoords(
                node, xField.getText(), yField.getText(), locationField.getText(), nodePoint);
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

    // System.out.println(node);
  }

   */

}
