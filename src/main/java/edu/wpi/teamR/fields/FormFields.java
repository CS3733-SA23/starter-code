package edu.wpi.teamR.fields;

public class FormFields {
  private String name;
  private String location;
  private String notes;

  public FormFields(String name, String location, String notes) {
    this.name = name;
    this.location = location;
    this.notes = notes;
  }

  public FormFields(String name, String location) {
    this.name = name;
    this.location = location;
    this.notes = "N/A";
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getNotes() {
    return notes;
  }
}
