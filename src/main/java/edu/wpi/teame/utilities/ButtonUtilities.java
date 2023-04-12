package edu.wpi.teame.utilities;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.paint.Color;

public class ButtonUtilities {
  /**
   * adds listeners for entering and exiting a given MFXButton that allow color change on hover (ie
   * the button and text switch colors)
   *
   * @param button the button that is being configured
   * @param backgroundColor the corresponding ColorPalette entry for the background color of the
   *     button
   * @param textColor the corresponding ColorPalette entry for the color of the text in the button
   */
  public static void addButtonHover(
      MFXButton button, ColorPalette backgroundColor, ColorPalette textColor, boolean leftAligned) {
    addButtonHover(button, backgroundColor.getHexCode(), textColor.getHexCode(), leftAligned);
  }

  /**
   * adds listeners for entering and exiting a given MFXButton that allow color change on hover (ie
   * the button and text switch colors)
   *
   * @param button the button that is being configured
   * @param backgroundHex the hex code for the background of the button (include the hash, eg
   *     "#ffee00" or "aaff1cff")
   * @param textHex the hex code for the text in the button (include the hash, eg "#ffee00" or
   *     "aaff1cff")
   */
  public static void addButtonHover(
      MFXButton button, String backgroundHex, String textHex, boolean leftAligned) {
    String alignment = leftAligned ? "center-left" : "center";

    button.setOnMouseEntered(
        event -> {
          button.setStyle(
              "-fx-background-color: "
                  + textHex
                  + "; -fx-alignment: "
                  + alignment
                  + "; -fx-border-color: "
                  + backgroundHex
                  + "; -fx-border-width: 2;");
          button.setTextFill(Color.web(backgroundHex, 1.0));
        });
    button.setOnMouseExited(
        event -> {
          button.setStyle(
              "-fx-background-color: " + backgroundHex + "; -fx-alignment: " + alignment + ";");
          button.setTextFill(Color.web(textHex, 1.0));
        });
  }

  /**
   * adds listeners for entering and exiting a given MFXButton that allow color change on hover (ie
   * the button and text switch colors)
   */
  public static void addButtonHover(MFXButton button) {
    String textHex = buttonText(button);
    String alignment = buttonTextAlignment(button);
    String backgroundHex = buttonBackground(button);

    button.setOnMouseEntered(
        event -> {
          button.setStyle(
              "-fx-background-color: "
                  + textHex
                  + "; -fx-alignment: "
                  + alignment
                  + "; -fx-border-color: "
                  + backgroundHex
                  + "; -fx-border-width: 2;");
          button.setTextFill(Color.web(backgroundHex, 1.0));
        });
    button.setOnMouseExited(
        event -> {
          button.setStyle(
              "-fx-background-color: " + backgroundHex + "; -fx-alignment: " + alignment + ";");
          button.setTextFill(Color.web(textHex, 1.0));
        });
  }

  // Helpers

  private static String buttonBackground(MFXButton button) {
    String style = button.getStyle();
    int loc = style.indexOf("-fx-background-color: ");
    loc += "-fx-background-color: ".length();
    // System.out.println("location: " + loc);
    // System.out.println("style length: " + style.length());
    return style.substring(loc, loc + 7) + "ff";
  }

  private static String buttonText(MFXButton button) {
    String text = button.getTextFill().toString();
    return "#" + text.substring(2);
  }

  public static String buttonTextAlignment(MFXButton button) {
    String style = button.getStyle();
    int loc = style.indexOf("-fx-alignment: ");
    loc += "-fx-alignment: ".length();

    if (loc == -1) return "center";

    // find the end index of the alignment field
    int end = style.substring(loc).indexOf(";");
    return style.substring(loc, loc + end);
  }
}
