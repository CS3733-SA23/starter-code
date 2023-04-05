package edu.wpi.teamc.serviceRequest;

public class ConferenceRoom {
  private String longName;

  private String shortName;

  private Boolean availability;

  // Should it be this.longName = longName? and this.shortName = shortName?
  public ConferenceRoom(String longName, String shortName, Boolean availability) {
    this.longName = longName;
    this.shortName = shortName;
    this.availability = availability;
  }
}
