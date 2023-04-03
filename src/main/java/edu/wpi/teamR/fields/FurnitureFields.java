package edu.wpi.teamR.fields;

public class FurnitureFields extends FormFields {
  private String furniture;

  public FurnitureFields(
      String name, String location, String staffMember, String notes, String furniture) {
    super(name, location, staffMember, notes);
    this.furniture = furniture;
  }

  public FurnitureFields(String name, String location, String furniture) {
    super(name, location);
    this.furniture = furniture;
  }

  public String getFurniture() {
    return furniture;
  }
}
