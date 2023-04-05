package edu.wpi.teamA.database.Connection;

import java.sql.Connection;

public interface IConnectionProvider {
  Connection getNodeConnection();

  Connection getEdgeConnection();

  Connection getLocationNameConnection();

  Connection getMoveConnection();
}
