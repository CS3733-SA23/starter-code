package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;

public class FlowerRequestController implements IPageController {
  @Override
  public void initialize() {}

  @Override
  public void back() {
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }
}
