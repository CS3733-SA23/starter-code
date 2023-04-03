package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.Edge;
import edu.wpi.teamA.database.Node;
import java.util.ArrayList;

public class SearchController {
    public class Wrapping {
        Node currentNode;
        Wrapping previousPath;
        boolean noPrevPath;

        public Wrapping(Node currentNode, Wrapping previousPath) {
            this.currentNode = currentNode;
            this.previousPath = previousPath;
            noPrevPath = false;
        }

        public Wrapping(Node currentNode) {
            this.currentNode = currentNode;
            noPrevPath = true;
        }

        public void addPath(Wrapping path) {
            this.previousPath = path;
            noPrevPath = false;
        }
    }

    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;

    public ArrayList pathOfNodes(Node startNode, Node endNode) {
        ArrayList<Node> queue = new ArrayList<Node>();
        ArrayList<Node> path = new ArrayList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        ArrayList<Wrapping> listOfPaths = new ArrayList<Wrapping>();

        Node currentNode = startNode;
        Wrapping currentPath = new Wrapping(currentNode);
        visited.add(currentNode);

        while (!currentNode.getNodeID() == endNode.getNodeID()) {

            for (int i = 0; i < currentNode.edgeCount(); i++) {

                Edge currentEdge = currentNode.getEdge(i);
                Node otherNode;

                if (currentEdge
                        .getStartNode()
                        .getNodeID()
                        .equals(
                                currentNode
                                        .getNodeID())) { // check whether node is starting node or ending node in the
                    // edge
                    otherNode = currentEdge.getEndNode();
                } else {
                    otherNode = currentEdge.getStartNode();
                }

                boolean otherVisited = false;
                for (int j = 0; j < visited.size(); j++) {
                    if (otherNode
                            .getNodeID() == visited.get(j).getNodeID()) { // check if node has been visited
                        otherVisited = true;
                    }
                }

                if (!otherVisited) { // if not visited, add to queue and add to wrapping queue
                    queue.add(otherNode);
                    Wrapping newPath = new Wrapping(otherNode, currentPath);
                    listOfPaths.add(newPath);
                }
            }

            visited.add(currentNode);
            currentNode = queue.remove(0);
            currentPath = listOfPaths.remove(0);
        }

        while (currentPath.noPrevPath == false) { // while the path still has a previous path
            path.add(0, currentPath.currentNode);
            currentPath = currentPath.previousPath;
        }
        path.add(0, startNode);

        return path;
    }
}
