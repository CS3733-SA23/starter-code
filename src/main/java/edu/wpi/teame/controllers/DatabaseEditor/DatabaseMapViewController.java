package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.Main;
import edu.wpi.teame.map.Floor;
import edu.wpi.teame.map.HospitalNode;
import edu.wpi.teame.map.LocationName;
import edu.wpi.teame.utilities.MapUtilities;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DatabaseMapViewController {

  @FXML AnchorPane lowerOneMapPane;
  @FXML AnchorPane lowerTwoMapPane;
  @FXML AnchorPane floorOneMapPane;
  @FXML AnchorPane floorTwoMapPane;
  @FXML AnchorPane floorThreeMapPane;

  @FXML Tab floorOneTab;
  @FXML Tab floorTwoTab;
  @FXML Tab floorThreeTab;
  @FXML Tab lowerLevelTwoTab;
  @FXML Tab lowerLevelOneTab;

  @FXML TabPane tabs;

  // Sidebar Elements
  @FXML VBox sidebar;
  // @FXML TextField longNameField;
  // @FXML TextField shortNameField;
  @FXML ComboBox<String> longNameSelector;
  @FXML MFXButton addLocationButton;
  @FXML TextField xField;
  @FXML TextField yField;
  @FXML MFXButton confirmButton;
  @FXML MFXButton deleteNodeButton;
  @FXML MFXButton addNodeButton;
  @FXML MFXButton cancelButton; // clicking will revert changes and close the sidebar
  // @FXML MFXButton refreshButton;
  @FXML HBox coordFields;

  @FXML HBox nameFields;

  @FXML TextField newLongNameField;
  @FXML TextField newShortNameField;
  @FXML ComboBox<LocationName.NodeType> nodeTypeChoice;

  @FXML ImageView mapImage; // Floor 1
  @FXML ImageView mapImage1; // Floor 2
  @FXML ImageView mapImage11; // Floor 3
  @FXML ImageView mapImage111; // Floor L1
  @FXML ImageView mapImage1111; // Floor L2

  Floor currentFloor;
  MapUtilities mapUtil;

  HospitalNode currentNode;
  Circle currentCircle;
  Label currentLabel;

  @FXML
  public void initialize() {
    mapUtil = new MapUtilities(lowerTwoMapPane);
    lowerLevelOneTab.setOnSelectionChanged(
        event -> {
          if (lowerLevelOneTab.isSelected()) refreshTab(Floor.LOWER_ONE);
        });
    lowerLevelTwoTab.setOnSelectionChanged(
        event -> {
          if (lowerLevelTwoTab.isSelected()) refreshTab(Floor.LOWER_TWO);
        });
    floorOneTab.setOnSelectionChanged(
        event -> {
          if (floorOneTab.isSelected()) refreshTab(Floor.ONE);
        });
    floorTwoTab.setOnSelectionChanged(
        event -> {
          if (floorTwoTab.isSelected()) refreshTab(Floor.TWO);
        });
    floorThreeTab.setOnSelectionChanged(
        event -> {
          if (floorThreeTab.isSelected()) refreshTab(Floor.THREE);
        });

    currentFloor = Floor.LOWER_TWO;
    // refreshButton.setOnMouseClicked(event -> loadFloorNodes(Floor.LOWER_ONE));
    sidebar.setVisible(false);

    // Sidebar functions
    cancelButton.setOnAction(event -> sidebar.setVisible(false));

    updateCombo();
    updateNodeTypeCombo();

    tabs.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> sidebar.setVisible(false));

    deleteNodeButton.setOnAction(
        event -> {
          SQLRepo.INSTANCE.deletenode(currentNode);
          refreshTab(currentFloor);
        });

    addNodeButton.setOnAction(
        event -> {
          // SQLRepo.INSTANCE.addNode(new HospitalNode("7000", 500, 500, currentFloor, "New node"));
          // SQLRepo.INSTANCE.addMove(new MoveAttribute("7000", "Test", "New node"));
          // TODO: HELLLLLLPPPPPPPPPP
        });

    // We love fixing bugs in the eleventh hour :)
    mapImage.setImage(
        new Image(String.valueOf(Main.class.getResource("maps/01_thefirstfloor.png"))));
    mapImage1.setImage(
        new Image(String.valueOf(Main.class.getResource("maps/02_thesecondfloor.png"))));
    mapImage11.setImage(
        new Image(String.valueOf(Main.class.getResource("maps/03_thethirdfloor.png"))));
    mapImage111.setImage(
        new Image(String.valueOf(Main.class.getResource("maps/00_thelowerlevel1.png"))));
    mapImage1111.setImage(
        new Image(String.valueOf(Main.class.getResource("maps/00_thelowerlevel2.png"))));
  }

  public void refreshTab(Floor newFloor) {
    currentLabel = null;
    currentCircle = null;
    currentNode = null;
    currentFloor = newFloor;
    //    mapUtil.removeAll(Circle.class);
    //    System.out.println("1");
    //    System.out.println(whichPane(currentFloor).getChildren());
    //    mapUtil.removeAll(Label.class);
    mapUtil.removeAll();
    //    System.out.println("2");
    //    System.out.println(whichPane(currentFloor).getChildren());
    mapUtil = new MapUtilities(whichPane(currentFloor));
    loadFloorNodes(newFloor);
    //    System.out.println("3");
    //    System.out.println(whichPane(currentFloor).getChildren());
  }

  private void editableNode(HospitalNode node) {
    Circle nodePoint = mapUtil.drawHospitalNode(node);
    Label nodeLabel = new Label();
    try {
      nodeLabel =
          mapUtil.createLabel(
              node.getXCoord(),
              node.getYCoord(),
              SQLRepo.INSTANCE.getShortNameFromNodeID(node.getNodeID()));
    } catch (SQLException e) {
      System.out.println(e);
    }
    Label finalNodeLabel = nodeLabel;

    nodePoint.setOnMouseClicked(
        event -> {
          displayMetadata(node, nodePoint);
          updateCurrentNode(node, nodePoint, finalNodeLabel);
        });

    nodeLabel.setOnMouseClicked(
        event -> {
          displayNameData(node, finalNodeLabel);
        });

    nodePoint.setOnMouseDragged(
        event -> {
          updateCurrentNode(node, nodePoint, finalNodeLabel);
          ((Circle) event.getSource()).setCenterX(event.getX());

          finalNodeLabel.setLayoutX(event.getX());

          ((Circle) event.getSource()).setCenterY(event.getY());
          finalNodeLabel.setLayoutY(event.getY());
          displayMetadata(node, nodePoint);
        });
    confirmButton.setOnAction(event -> updateCoordinates(node, xField.getText(), yField.getText()));
  }

  private void displayMetadata(HospitalNode node, Circle nodePoint) {
    // longNameField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));

    xField.setText((int) mapUtil.PaneXToImageX(nodePoint.getCenterX()) + "");
    yField.setText((int) mapUtil.PaneYToImageY(nodePoint.getCenterY()) + "");
    sidebar.setVisible(true);
    coordFields.setVisible(true);
    nameFields.setVisible(false);
  }

  private void displayNameData(HospitalNode node, Label nodeLabel) {
    longNameSelector.setValue(
        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));

    confirmButton.setOnAction(event -> updateName(node, longNameSelector.getValue()));

    addLocationButton.setOnAction(
        event -> {
          String longName = newLongNameField.getText();
          String shortName = newShortNameField.getText();
          LocationName.NodeType nodeType = nodeTypeChoice.getValue();
          LocationName locationName = new LocationName(longName, shortName, nodeType);
          SQLRepo.INSTANCE.addLocation(locationName);
          updateCombo();
        });

    sidebar.setVisible(true);
    coordFields.setVisible(false);
    nameFields.setVisible(true);
  }

  private void updateMetadata(HospitalNode node, Circle nodePoint) {
    // longNameField.setText(SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())));

    xField.setText(mapUtil.PaneXToImageX(nodePoint.getCenterX()) + "");
    yField.setText(mapUtil.PaneYToImageY(nodePoint.getCenterY()) + "");
  }

  public void loadFloorNodes(Floor floor) {
    //    mapUtil.removeAllByType(Circle.class);
    //    mapUtil.removeAllByType(Label.class);
    mapUtil.removeAll();
    List<HospitalNode> nodes = SQLRepo.INSTANCE.getNodesFromFloor(floor);
    for (HospitalNode node : nodes) {
      String nodeTypeString =
          SQLRepo.INSTANCE.getNodeTypeFromNodeID(Integer.parseInt(node.getNodeID()));
      if (!nodeTypeString.equals("")) {
        LocationName.NodeType nodeType = LocationName.NodeType.stringToNodeType(nodeTypeString);
        if (nodeType == LocationName.NodeType.HALL) {
          continue;
        }
      }

      editableNode(node);
    }
  }

  private void refreshNode(HospitalNode node, Circle nodeCircle, Label nodeLabel) {
    nodeCircle.setCenterX(mapUtil.convertX(node.getXCoord()));
    nodeCircle.setCenterY(mapUtil.convertY(node.getYCoord()));
    nodeLabel.setLayoutX(mapUtil.convertX(node.getXCoord()));
    nodeLabel.setLayoutY(mapUtil.convertY(node.getYCoord()));
  }

  private void updateCurrentNode(HospitalNode node, Circle circle, Label label) {
    if (currentNode != null && !currentNode.equals(node)) {
      refreshNode(currentNode, currentCircle, currentLabel);
      for (Node nodeCircle : mapUtil.filterShapes(Circle.class)) {
        Circle aCircle = ((Circle) nodeCircle);
        aCircle.setFill(Color.BLACK);
      }
    }
    circle.setFill(Color.RED);
    currentNode = node;
    currentCircle = circle;
    currentLabel = label;
  }

  /*
  private void updateNodeDatabase() {
    for (int i = 0; i < modifiedNodes.size(); i++) {
      editFromNode(modifiedNodes.get(i));
    }
    for (HospitalNode node : modifiedNodes) {
      editFromNode(node);
    }
  }

   */

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

  private void updateCoordinates(HospitalNode node, String x, String y) {
    System.out.println(whichPane(currentFloor).getChildren());
    SQLRepo.INSTANCE.updateNode(node, "xcoord", x);
    SQLRepo.INSTANCE.updateNode(node, "ycoord", y);
    //    whichPane(currentFloor).getChildren().remove(mapUtil.getCurrentNodes());
    refreshTab(currentFloor);
    System.out.println(whichPane(currentFloor).getChildren());
  }

  private void updateName(HospitalNode node, String newName) {
    SQLRepo.INSTANCE.updateUsingNodeID(
        node.getNodeID(),
        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())),
        "longName",
        newName);
    refreshTab(currentFloor);
  }

  private void updateCombo() {
    List<LocationName> locationNames = SQLRepo.INSTANCE.getLocationList();
    List<String> longNames = SQLRepo.INSTANCE.getLongNamesFromLocationName(locationNames);
    longNameSelector.setItems(FXCollections.observableList(longNames));
  }

  private void updateNodeTypeCombo() {
    List<LocationName.NodeType> nodeComboList = new ArrayList<>();
    nodeComboList.add(LocationName.NodeType.BATH);
    nodeComboList.add(LocationName.NodeType.CONF);
    nodeComboList.add(LocationName.NodeType.DEPT);
    nodeComboList.add(LocationName.NodeType.ELEV);
    nodeComboList.add(LocationName.NodeType.EXIT);
    nodeComboList.add(LocationName.NodeType.HALL);
    nodeComboList.add(LocationName.NodeType.INFO);
    nodeComboList.add(LocationName.NodeType.LABS);
    nodeComboList.add(LocationName.NodeType.REST);
    nodeComboList.add(LocationName.NodeType.RETL);
    nodeComboList.add(LocationName.NodeType.SERV);
    nodeComboList.add(LocationName.NodeType.STAI);

    nodeTypeChoice.setItems(FXCollections.observableArrayList(nodeComboList));
  }

  private void editFromNode(HospitalNode node) {
    SQLRepo.INSTANCE.updateNode(node, "xcoord", node.getXCoord() + "");
    System.out.println("theoretical new x coord: " + node.getXCoord());
    SQLRepo.INSTANCE.updateNode(node, "ycoord", node.getYCoord() + "");
    System.out.println("theoretical new y coord: " + node.getYCoord());

    //    // update the move name
    //    SQLRepo.INSTANCE.updateUsingNodeID(
    //        node.getNodeID(),
    //        SQLRepo.INSTANCE.getNamefromNodeID(Integer.parseInt(node.getNodeID())),
    //        "longName",
    //        longName);
    //    SQLRepo.INSTANCE.updateLocation(locationName, "shortName", locationName.getShortName());
  }

  public AnchorPane whichPane(Floor curFloor) {
    switch (curFloor) {
      case ONE:
        return floorOneMapPane;
      case TWO:
        return floorTwoMapPane;
      case THREE:
        return floorThreeMapPane;
      case LOWER_ONE:
        return lowerOneMapPane;
      case LOWER_TWO:
        return lowerTwoMapPane;
    }
    return floorOneMapPane;
  }
}
