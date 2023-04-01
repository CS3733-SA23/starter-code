package edu.wpi.teamR.fields;

public class FormFields {
  private String name;
  private String location;
  private String staffMember;
  private String notes;

  public FormFields(String name, String location, String staffMember, String notes) {
    this.name = name;
    this.location = location;
    this.staffMember = staffMember;
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

  public String getStaffMember() {
    return staffMember;
  }

  public String getNotes() {
    return notes;
  }
}
