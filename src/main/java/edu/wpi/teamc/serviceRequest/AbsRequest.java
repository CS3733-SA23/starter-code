package edu.wpi.teamc.serviceRequest;

import lombok.Getter;
import lombok.Setter;

public class AbsRequest implements IServiceRequest {
  @Getter private Requester requester;
  @Getter @Setter private STATUS stat;

  public AbsRequest(Requester requester, STATUS stat) {
    this.requester = requester;
    this.stat = stat;
  }
}
