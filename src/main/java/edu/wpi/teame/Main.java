package edu.wpi.teame;

import Database.DatabaseController;
import Database.DatabaseGraphController;
import edu.wpi.teame.map.HospitalNode;

public class Main {

  public static void main(String[] args) {
    DatabaseController db = new DatabaseController();
    DatabaseGraphController graphController = new DatabaseGraphController(db);
    graphController.retrieveFromTable();
    HospitalNode.processEdgeList(graphController.getHospitalEdges());
    System.out.println(HospitalNode.allNodes);
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
