package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest implements IServiceRequest {
  @Getter @Setter private String startTime;
  @Getter @Setter private String endTime;
  @Getter @Setter private String additionalNotes;

  @Getter @Setter private String roomName;
  @Getter @Setter private STATUS stat;

  public ConferenceRoomRequest(
      String startTime, String endTime, String additionalNotes, String roomName, STATUS stat) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.additionalNotes = additionalNotes;
    this.roomName = roomName;
    this.stat = stat;
  }
}
