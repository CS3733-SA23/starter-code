package edu.wpi.teamname.boundary;

import edu.wpi.teamname.controllers.AlgorithmController;
import edu.wpi.teamname.entity.Node;
import java.util.ArrayList;
import java.util.Scanner;

public class AlgorithmCLI {
  public static void main(String[] args) {

    Scanner reader = new Scanner(System.in);
    AlgorithmController aController = new AlgorithmController();

    System.out.println("Welcome to the hospital pathfinding proof of concept. ");

    while (true) {
      // starting and ending node long names
      String node1, node2;

      // get the LongName for the starting node from the user, ensuring it exists
      System.out.println("Enter the long name of the starting node: ");
      while (true) {
        node1 = reader.nextLine();

        if (aController.nodeExists(node1)) break;
        else System.out.println("Node with ID " + node1 + " does not exist. Please try again.");
      }

      // get the LongName for the ending node from the user, ensuring it exists
      System.out.println("Enter the long name of the ending node: ");
      while (true) {
        node2 = reader.nextLine();

        if (aController.nodeExists(node2)) break;
        else System.out.println("Node with ID " + node2 + " does not exist. Please try again.");
      }

      // the solution set of nodes
      ArrayList<Node> solutionPath = new ArrayList<>();
      boolean finished = false;

      System.out.println(
          "Which algorithm would you like to use? \n"
              + "1: Breadth First Search \n"
              + "2: Depth First Search \n"
              + "3: A* Search \n");
      while (!finished) {
        String choice = reader.nextLine();

        switch (choice) {
          case "1":
            solutionPath = aController.breadthFirstSearch(node1, node2);
            finished = true;
            break;
          case "2":
          case "3":
            // TODO: replace with actual references to these algorithms when they are implemented
            System.out.println("This algorithm has not been implemented yet. Please try again.");
            break;
          default:
            System.out.println("This is not a recognized choice. Please try again.");
        }
      }

      // print solution path
      System.out.println("Path from starting to ending node: ");
      for (Node n : solutionPath) {
        System.out.println(n.getLongName());
      }

      System.out.println("Would you like to run again? (Y/N)");
      if (reader.nextLine().toUpperCase().charAt(0) != 'Y') {
        System.out.println("Thank you for using the hospital pathfinding proof of concept.");
        break;
      }
    }
  }
}
