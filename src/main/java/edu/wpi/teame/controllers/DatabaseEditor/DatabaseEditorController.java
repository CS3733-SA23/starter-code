package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.map.Floor;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DatabaseEditorController {
  @FXML MFXButton backButton;

  @FXML TabPane tabPane;
  @FXML Tab editMapTab;

  @FXML Tab editDatabaseTab;

  @FXML DatabaseMapViewController mapViewController;

  @FXML
  public void initialize() {

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<Tab>() {
              @Override
              public void changed(
                  ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                if (newTab == editMapTab) {
                  mapViewController.loadFloorNodes(Floor.LOWER_ONE);
                  AnchorPane mapPane = mapViewController.mapPane;
                  ImageView imageView = mapViewController.imageView;
                  System.out.println("map width: " + mapPane.getWidth());
                  System.out.println("map height: " + mapPane.getHeight());
                  System.out.println("image width " + imageView.getFitWidth());
                  System.out.println("image height: " + imageView.getFitHeight());
                }
              }
            });

    //    editDatabaseTab.setOnMouseClicked(event -> mapViewController.loadFloorNodes(Floor.ONE));
  }
}
