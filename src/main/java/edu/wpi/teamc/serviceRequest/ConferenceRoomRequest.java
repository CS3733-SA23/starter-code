package edu.wpi.teamc.serviceRequest;

import java.sql.Date;

// import java.util.Date;

public class ConferenceRoomRequest implements IServiceRequest {
  private ConferenceRoom selection;

  private Date date;

  private String startTime;
  private String endTime;
  private String addtionalNotes;
  private STATUS stat;

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

  public ConferenceRoom getSelection() {
    return selection;
  }

  public void setSelection(ConferenceRoom selection) {
    this.selection = selection;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getAddtionalNotes() {
    return addtionalNotes;
  }

  public void setAddtionalNotes(String addtionalNotes) {
    this.addtionalNotes = addtionalNotes;
  }

  public String getStat() {
    return stat.name();
  }
}
