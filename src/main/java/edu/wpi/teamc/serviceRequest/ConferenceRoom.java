package edu.wpi.teamc.serviceRequest;

import lombok.Getter;

public class ConferenceRoom {
  @Getter private String longName;
  @Getter private String shortName;
  @Getter private Boolean availability;

  // Should it be this.longName = longName? and this.shortName = shortName?
  public ConferenceRoom(String longName, String shortName, Boolean availability) {
    this.longName = longName;
    this.shortName = shortName;
    this.availability = availability;
  }

  @Override
  public String toString() {
    return this.longName;
  }
}
