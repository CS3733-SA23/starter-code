package edu.wpi.teamA.controllers.Navigation;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public interface IServiceController {
  @FXML MFXButton submitButton = new MFXButton();
  @FXML MFXButton clearButton = new MFXButton();

  @FXML
  public void validateButton();

  public void clear();

  public void submit();
}
