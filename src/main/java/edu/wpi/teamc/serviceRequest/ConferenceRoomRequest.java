package edu.wpi.teamc.serviceRequest;

import java.sql.Date;

// import java.util.Date;

public class ConferenceRoomRequest implements IServiceRequest {
  private ConferenceRoom selection;

  private Date date;

  private String startTime;

  private String endTime;

  private String addtionalNotes;

  public ConferenceRoomRequest(
      ConferenceRoom select, Date date, String startTime, String endTime, String addtionalNotes) {
    selection = select;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.addtionalNotes = addtionalNotes;
  }

  // need to change availability to false
}
