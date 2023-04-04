package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.database.Edge;
import edu.wpi.teamA.database.LocationName;
import edu.wpi.teamA.database.Move;
import edu.wpi.teamA.database.Node;
import lombok.Getter;
import lombok.Setter;

public class TableData {
  @Getter @Setter private Node node;
  @Getter @Setter private LocationName locationName;
  @Getter @Setter private Move move;
  @Getter @Setter private Edge edgeData;

  public TableData(Node node, LocationName locationName, Move move, Edge edgeData) {
    this.node = node;
    this.locationName = locationName;
    this.move = move;
    this.edgeData = edgeData;
  }
}
