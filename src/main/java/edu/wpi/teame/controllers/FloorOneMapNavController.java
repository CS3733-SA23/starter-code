package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import edu.wpi.teame.navigation.Screen;

public class FloorOneMapNavController {
    @FXML
    private MFXButton lowerLevelTwoButton;

    @FXML
    private MFXButton SecondFloorButton;

    @FXML
    private MFXButton thirdFloorButton;

    @FXML
    private MFXButton backButton;

    @FXML
    private MFXButton groundFloorButton;

    @FXML
    private MFXButton lowerLevelOneButton;


    @FXML
    public void initialize()
    {
        lowerLevelOneButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_ONE));
        lowerLevelTwoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOWER_TWO));
        groundFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));
        SecondFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_TWO));
        thirdFloorButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOOR_THREE));
        backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    }
}
