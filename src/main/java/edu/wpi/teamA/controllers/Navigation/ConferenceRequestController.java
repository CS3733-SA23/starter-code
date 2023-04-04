package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ConferenceRequestController implements IPageController, IServiceController {
  @FXML MFXButton submitButton;
  @FXML MFXTextField nameField;
  @FXML MFXTextField roomField;
  @FXML MFXDatePicker datePicker;
  @FXML MFXComboBox startCombo;
  @FXML MFXComboBox endCombo;
  @FXML MFXTextField commentField;

  @Override
  public void initialize() {
    startCombo
        .getItems()
        .addAll(
            "00:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00",
            "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
            "19:00", "20:00", "21:00", "22:00", "23:00");
    endCombo
        .getItems()
        .addAll(
            "00:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00",
            "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
            "19:00", "20:00", "21:00", "22:00", "23:00");
  }

  @Override
  public void back() {
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }

  public void clear() {
    submitButton.setDisable(true);
    nameField.clear();
    roomField.clear();
    commentField.clear();
    startCombo.getSelectionModel().clearSelection();
    endCombo.getSelectionModel().clearSelection();
    datePicker.clear();
  }

  public void validateButton() {
    if (nameField.getText().isEmpty()
        || datePicker.getText().isEmpty()
        || startCombo.getSelectedIndex() == -1
        || endCombo.getSelectedIndex() == -1) {
      submitButton.setDisable(true);
    } else {
      try {
        Integer.parseInt(roomField.getText());
        submitButton.setDisable(false);
      } catch (NumberFormatException error) {
        submitButton.setDisable(true);
      }
    }
  }

  public void submit() {}
}
