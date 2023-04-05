package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class Requester {
  @Getter private int requesterID;
  @Getter @Setter private String requesterName;

  public Requester(int requesterID, String requesterName) {
    this.requesterID = requesterID;
    this.requesterName = requesterName;
  }

  @Override
  public String toString() {
    return Integer.toString(this.requesterID);
  }
}
