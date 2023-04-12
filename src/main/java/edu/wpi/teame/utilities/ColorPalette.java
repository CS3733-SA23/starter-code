package edu.wpi.teame.utilities;

public enum ColorPalette {
  DARK_BLUE("#192d5a"),
  BLUE("#223b95"),
  LIGHT_BLUE("#3b63a5"),
  CYAN("#5c9ca6"),
  YELLOW("#e5bc3b"),
  WHITE("#ffffff"),
  LIGHT_GRAY("#f1f1f1"),
  GRAY("#2f2f2f");

  private final String hexCode;

  ColorPalette(String hexCode) {
    this.hexCode = hexCode;
  }

  public String getHexCode() {
    return hexCode;
  }
}
