package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest implements IServiceRequest {
  @Getter @Setter private Requester requester;
  @Getter @Setter private ConferenceRoom conferenceRoom;
  @Getter @Setter private String startTime;
  @Getter @Setter private String endTime;
  @Getter @Setter private String additionalNotes;

  @Getter @Setter private String roomName;
  @Getter @Setter private STATUS stat;

  public ConferenceRoomRequest(

      Requester requester,
      ConferenceRoom conferenceRoom,
      String startTime,
      String endTime,
      String additionalNotes,
      STATUS stat) {
    this.requester = requester;
    this.conferenceRoom = conferenceRoom;
    this.startTime = startTime;
    this.endTime = endTime;
    this.additionalNotes = additionalNotes;
    this.roomName = roomName;
    this.stat = stat;
  }
}
