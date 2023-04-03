package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class logInController {

    @FXML MFXButton cancelButton;
    @FXML MFXButton logInButton;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

    }
}
