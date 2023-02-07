package edu.wpi.teamname;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import java.io.IOException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TestFXExample extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    new App().start(stage);
  }

  @Test
  public void test1() {
    // click on the node with the id "navigateButton"
    clickOn("#navigateButton");
    // verify that a node with id "backButton" is visible
    verifyThat("#backButton", isVisible());
  }
}
