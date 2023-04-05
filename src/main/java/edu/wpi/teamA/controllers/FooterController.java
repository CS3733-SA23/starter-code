package edu.wpi.teamA.controllers;

import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FooterController {
  @FXML ImageView aboutButton;
  @FXML ImageView helpButton;
  @FXML Text timeText;

  public void initialize() throws InterruptedException {
    updateTime();
  }

  private String getTime() {
    int second = LocalDateTime.now().getSecond();
    int minute = LocalDateTime.now().getMinute();
    int hour = LocalDateTime.now().getHour();
    int day = LocalDateTime.now().getDayOfMonth();
    int month = LocalDateTime.now().getMonthValue();
    int year = LocalDateTime.now().getYear();
    String time = month + "/" + day + "/" + year + " " + hour + ":" + minute + ":" + second;
    return time;
  }

  public void updateTime() throws InterruptedException {
    timeText.setText(getTime());
  }
}
