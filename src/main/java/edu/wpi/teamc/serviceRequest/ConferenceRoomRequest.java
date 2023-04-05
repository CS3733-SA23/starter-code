package edu.wpi.teamc.serviceRequest;

public class ConferenceRoomRequest implements IServiceRequest {
  private String startTime;
  private String endTime;
  private String additionalNotes;
  private STATUS stat;

  public ConferenceRoomRequest(
      String startTime, String endTime, String additionalNotes, STATUS stat) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.additionalNotes = additionalNotes;
    this.stat = stat;
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
    return additionalNotes;
  }

  public void setAddtionalNotes(String addtionalNotes) {
    this.additionalNotes = addtionalNotes;
  }

  public String getStat() {
    return stat.name();
  }
}
