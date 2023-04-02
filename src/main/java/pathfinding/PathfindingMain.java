package pathfinding;

import Database.DatabaseController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PathfindingMain {
  public static void main(String[] args) {
    System.out.println(
        "----------------------------------------------------"
            + "\nRunning Program, input commands:");
    Scanner scanner = new Scanner(System.in);
    scannerHandler(scanner);
  }

  public static void scannerHandler(Scanner scanner) {
    while (scanner.hasNextLine()) {
      // Read the next line of command line input and use it to perform the next operation
      String input = scanner.nextLine();

      if (input == null)
        // Just to make sure that we never get a NullPointerException within one of the helpers
        continue;

      // Can't use a switch case because we only want to look at the start of the string, not the
      // full string
      // Could be done with input.split(" ")[0], but I think that's a little less readable code-side
      // Could also be done with a regex I think, but that seems way too complicated for a
      // prototype-only feature

      if (input.startsWith("bfs")) {
        // Run BFS between the two specified nodes
        bfsHandler(input);

      } else if (input.startsWith("astar")) {
        // Run A* between the two specified nodes
        aStarHandler(input);

      } else if (input.startsWith("add")) {
        // Two possible things to add: node or edge
        if (input.contains("-node")) {
          // Add a node with the specified ID
          nodeAddHandler(input);

        } else if (input.contains("-edge")) {
          // Add an edge between the two specified nodes, with a specified weight (optional)
          edgeAddHandler(input);
        }

      } else if (input.startsWith("import")) {
        importDatabaseHandler(input);

      } else if (input.startsWith("run")) {
        // Import a graph from a specified file
        runFromFile(input);

      } else if (input.startsWith("help")) {
        // Display a help menu
        helpDisplay();

      } else if (input.startsWith("quit")) {
        // Quit the program
        System.out.println("Quitting Program");
        return;
      }
    }
  }

  public static void bfsHandler(String input) {
    String[] arguments = input.split(" ");
    AbstractPathfinder pathfinder = new BFSPathfinder();
    System.out.println(pathfinder.findPath(arguments[1], arguments[2]));
  }

  public static void aStarHandler(String input) {
    String[] arguments = input.split(" ");
    AbstractPathfinder pathfinder = new AStarPathfinder();
    System.out.println(pathfinder.findPath(arguments[1], arguments[2]));
  }

  public static void nodeAddHandler(String input) {
    String[] arguments = input.split(" ");
    // add -node [nodeID] [xCoord] [yCoord]
    // No need to save the node, as it gets saved automatically in the allNodes collection
    if (arguments.length >= 5)
      new HospitalNode(
          arguments[2], Integer.parseInt(arguments[3]), Integer.parseInt(arguments[4]));
    else new HospitalNode(arguments[2]);
  }

  public static void edgeAddHandler(String input) {
    String[] arguments = input.split(" ");
    // add -edge [nodeOneId] [nodeTwoId] [edgeWeight]
    // If there are enough arguments, include an edge weight
    HospitalNode.addEdge(
        arguments[2], arguments[3], (arguments.length >= 5) ? Integer.parseInt(arguments[4]) : 1);
  }

  public static void importDatabaseHandler(String input) {
    String[] arguments = input.split(" ");
    DatabaseController db;
    // import [username (optional)] [password (optional)]
    if (arguments.length >= 3) db = new DatabaseController(arguments[1], arguments[2]);
    else db = new DatabaseController("teame", "teame50");
    // db.retrieveFromTable();
    // HospitalNode.processEdgeList(db.getHospitalEdges());
  }

  public static void runFromFile(String input) {
    // Runs the scanner handler on the contents of the file
    String filepath = input.substring(4);
    try {
      Scanner fileScanner = new Scanner(new File(filepath));
      scannerHandler(fileScanner);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }

  public static void helpDisplay() {
    System.out.println("Usage:");
    System.out.println("\tbfs [nodeID] [nodeID] - Runs BFS between the two specified nodes");
    System.out.println("\tastar [nodeID] [nodeID] - Runs A* between the two specified nodes");
    System.out.println(
        "\tadd -node [newNodeID] [xCoord (optional)] [yCoord (optional)] "
            + "- Creates a new node with the given ID and 0 edges");
    System.out.println(
        "\tadd -edge [nodeID] [nodeID] [edgeWeight (optional)] "
            + "- Creates an edge between the two nodes with the specified weight");
    System.out.println("\trun [fileName] - Runs a series of these commands stored in a text file");
    System.out.println("\thelp - Displays this menu");
    System.out.println("\tquit - Exits the program");
  }
}
