package edu.wpi.teamR.controllers;

import edu.wpi.teamR.Main;
import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.*;

public class MapController {
  @FXML MFXButton resetButton;
  @FXML MFXButton searchButton;
  @FXML MFXButton floorUpButton;
  @FXML MFXButton floorDownButton;

  @FXML MFXButton homeButton;
  @FXML BorderPane borderPane;
  @FXML AnchorPane anchorPane;

  @FXML GesturePane gesturePane;

  @FXML
  public void initialize() {
    URL imagePath = Main.class.getResource("images/00_thegroundfloor.png");
    ImageView image = new ImageView(imagePath.toExternalForm());
    gesturePane.setContent(image);

    resetButton.setOnMouseClicked(event -> reset());
    searchButton.setOnMouseClicked(event -> search());
    floorDownButton.setOnMouseClicked(event -> displayFloorDown());
    floorUpButton.setOnMouseClicked(event -> displayFloorUp());
    homeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  // Reset to original zoom
  public void reset() {
    // zoom to 1x
    gesturePane.zoomTo(1, 1, new Point2D(42, 42));
    gesturePane.zoomTo(1, 1, new Point2D(42, 42));
    // centre on point [42,42]
    gesturePane.centreOn(new Point2D(42, 42));
  }

  // zoom into a desired location
  // TODO fix zoomTo error
  public void animateZoomTo(int x, int y, int time) {
    // gesturePane.animate(Duration.millis(time)).zoomTo(1);
    // animate with some options
    gesturePane
        .animate(Duration.millis(time))
        .interpolateWith(Interpolator.EASE_BOTH)
        .afterFinished(() -> System.out.println("Done!"))
        .centreOn(new Point2D(x, y));
  }

  public void search() {
    /*TODO
    take info from fields
    calculate route
    find spread of nodes on current floor
    animateZoom to show all nodes on this floor
    create path between nodes on ALL floors
    create/display textual path? (would have to add spot to display)
     */
  }

  public void displayFloorUp() {
    /*TODO
    combine all maps into one?
    if(current floor is not the top)
        animateZoom to next highest floor
    else
        make it known to user in some way they cant go farther
     */
  }

  public void displayFloorDown() {
    /*TODO
    combine all maps into one?
    if(current floor is not the top)
        animateZoom to next highest floor
    else
        make it known to user in some way they cant go farther
     */
  }
}
