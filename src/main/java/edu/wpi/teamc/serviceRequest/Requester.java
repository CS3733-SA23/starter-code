package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class Requester {
  @Getter @Setter public int requesterID;
  @Getter @Setter public String requesterName;

  public Requester(int requesterID, String requesterName) {
    this.requesterID = requesterID;
    this.requesterName = requesterName;
  }
}
