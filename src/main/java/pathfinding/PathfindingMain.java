package pathfinding;

import java.util.Scanner;

public class PathfindingMain {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while(true){
      String input = scanner.nextLine();
      if(input.startsWith("bfs")){
        // Run BFS between the two specified nodes
        bfsHandler(input);
      } else if(input.startsWith("astar")){
        // Run A* between the two specified nodes
        astarHandler(input);
      } else if(input.startsWith("add")){
        if(input.contains("-node")){
          // Add a node with the specified ID
          nodeAddHandler(input);
        }else if(input.contains("-edge")){
          // Add an edge between the two specified nodes, with a specified weight (optional)
          edgeAddHandler(input);
        }
      } else if(input.startsWith("load")){
        // Import a graph from a specified file
        loadGraphFromFile(input);
      } else if(input.startsWith("help")){
        // Display a help menu
        helpDisplay();
      } else if(input.startsWith("quit")){
        // Quit the program
        System.out.println("Quitting Program");
        return;
      }
    }
  }

  public static void bfsHandler(String input){

  }

  public static void astarHandler(String input){

  }

  public static void nodeAddHandler(String input){
    
  }

  public static void edgeAddHandler(String input){

  }

  public static void loadGraphFromFile(String input){

  }

  public static void helpDisplay(){

  }

}
