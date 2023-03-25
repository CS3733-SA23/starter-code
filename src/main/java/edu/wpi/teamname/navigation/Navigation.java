package edu.wpi.teamname.navigation;

import edu.wpi.teamname.CApp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = CApp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      CApp.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
