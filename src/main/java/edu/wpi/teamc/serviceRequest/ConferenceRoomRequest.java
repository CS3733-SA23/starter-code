package edu.wpi.teamc.serviceRequest;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class ConferenceRoomRequest implements IServiceRequest {
  @Getter @Setter private ConferenceRoom selection;
  @Getter @Setter private Date date;
  @Getter @Setter private String startTime;
  @Getter @Setter private String endTime;
  @Getter @Setter private String addtionalNotes;
  @Getter @Setter private STATUS stat;

  public ConferenceRoomRequest(
      ConferenceRoom select,
      Date date,
      String startTime,
      String endTime,
      String addtionalNotes,
      STATUS stat) {
    selection = select;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.addtionalNotes = addtionalNotes;
    this.stat = stat;
  }
}
