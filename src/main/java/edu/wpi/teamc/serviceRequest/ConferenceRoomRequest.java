package edu.wpi.teamc.serviceRequest;

import java.sql.Date;

public class ConferenceRoomRequest {
  public ConferenceRoom selection;

  public Date date;

  public String startTime;

  public String endTime;

  public String addtionalNotes;

  public ConferenceRoomRequest(
      ConferenceRoom select, Date date, String startTime, String endTime, String addtionalNotes) {
    selection = select;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.addtionalNotes = addtionalNotes;
  }
}
