package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Map;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.kurobako.gesturefx.GesturePane;

public class MapController {

  Map map;
  @FXML GesturePane gp;
  @FXML AnchorPane anchor;
  @FXML HBox SelectCombo = new HBox();
  @FXML MFXComboBox<String> LocationOne = new MFXComboBox<>();
  @FXML MFXComboBox<String> EndPointSelect = new MFXComboBox<>();

  @FXML MFXComboBox<String> FloorSelect = new MFXComboBox<>();

  String defaultFloor = "L1";

  int clickCount = 0;
  Point2D firstClick = null;
  Point2D secondClick = null;

  String floor1;
  String floor2;

  String currFloor = "L1";

  int sNode = 0;
  int eNode = 0;

  EventHandler<MouseEvent> e =
      new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          clickCount++;

          if (clickCount == 1) {
            if (!map.getPrevPath().isEmpty()) {
              for (int i = anchor.getChildren().size() - 1; i >= 0; i--) {
                if (map.getPrevPath().contains(anchor.getChildren().get(i))) {
                  anchor.getChildren().remove(i);
                }
              }
              map.setPrevPath(null);
            }

            // Capture the first click
            firstClick = new Point2D(event.getX(), event.getY());
            LocationOne.setOnAction(e -> {});

            floor1 = takeFloor(FloorSelect.getValue());

          } else if (clickCount == 2) {
            // Capture the second click
            secondClick = new Point2D(event.getX(), event.getY());

            // Reset click counter
            clickCount = 0;

            floor2 = takeFloor(FloorSelect.getValue());

            // Call drawAStarPath with both points
            map.drawAStarPath(anchor, firstClick, secondClick, floor1, floor2);
          }
        }
      };

  EventHandler<ActionEvent> changeStart =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("T");
          System.out.println(LocationOne.getValue());
          // System.out.println(EndPointSelect.getValue());
          sNode = Integer.parseInt(LocationOne.getValue());
          if (sNode != 0 && eNode != 0) {
            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
          }
        }
      };

  EventHandler<ActionEvent> changeEnd =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("T");
          System.out.println(EndPointSelect.getValue());
          eNode = Integer.parseInt(EndPointSelect.getValue());
          if (sNode != 0 && eNode != 0) {
            map.drawAStarPath(anchor, floor1, floor2, sNode, eNode);
          }
        }
      };

  public String takeFloor(String f) {
    String retStr = "";
    if (f == null) {
      return "L1";
    }
    //    System.out.println(f);
    switch (f) {
      case ("Lower Level 1"):
        retStr = "L1";
        return retStr;
      case ("Lower Level 2"):
        retStr = "L2";
        return retStr;
      case ("Ground Floor"):
        retStr = "GG";
        return retStr;
      case ("First Floor"):
        retStr = "G1";
        return retStr;
      case ("Second Floor"):
        retStr = "G2";
        return retStr;
      case ("Third Floor"):
        retStr = "G3";
        return retStr;
      default:
        return "You should never see  this!!!";
    }
  }

  EventHandler<ActionEvent> changeFloor =
      new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
          System.out.println("CF");
          String floor = FloorSelect.getValue();
          System.out.println(floor);
          try {
            LocationOne.setItems(map.getAllNodeNames(takeFloor(floor)));
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }
          try {
            EndPointSelect.setItems(map.getAllNodeNames(takeFloor(floor)));
          } catch (SQLException ex) {
            throw new RuntimeException(ex);
          }

          anchor.getStyleClass().remove(0);
          anchor.getStyleClass().add(takeFloor(floor));

          //                           System.out.println(LocationOne.getValue());

        }
      };

  EventHandler<MouseEvent> checkPoints =
      new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
          System.out.println("M");
        }
      };

  @FXML
  public void initialize() throws SQLException {

    map = new Map();

    //    AnchorPane.setLeftAnchor(SelectCombo, 0.0);
    //    AnchorPane.setRightAnchor(SelectCombo, 0.0);
    //    AnchorPane.setTopAnchor(SelectCombo, 100.0);

    //    anchor.getChildren().add(SelectCombo);
    //    AnchorPane.setTopAnchor(anchor, 0.0);
    //    AnchorPane.setBottomAnchor(anchor, 1000.0);

    gp.setMinScale(0.11);
    anchor.setOnMouseClicked(e);

    //    gp.setOnMouseMoved(checkPoints);

    //    anchor.getChildren().addAll(map.makeAllFloorNodes(defaultFloor));

    map.centerAndZoom(gp);

    //    LocationOne.setStyle("-fx-padding: 5 250 5 5;");
    LocationOne.setPromptText("Select start");
    LocationOne.setItems(
        map.getAllNodeNames("L1")); // change for when the floor changes to update the nodes shown
    LocationOne.setOnAction(changeStart);

    EndPointSelect.setPromptText("Select end");
    EndPointSelect.setItems(map.getAllNodeNames("L1"));
    EndPointSelect.setOnAction(changeEnd);

    FloorSelect.setPromptText("Select floor");
    FloorSelect.setItems(map.getAllFloors("L1"));
    FloorSelect.setOnAction(changeFloor);

    //    System.out.println(getAllNodeNames("L1"));

    ParentController.titleString.set("Map");
  }
}
