package edu.wpi.teamA.pathfinding;

import edu.wpi.teamA.database.Edge;
import java.util.ArrayList;

public class SearchController {

    private Graph graph;

    public SearchController(Graph graph) {
        this.graph = graph;
    }


//    public class Wrapping {
//        Node currentNode;
//        Wrapping previousPath;
//        boolean noPrevPath;
//
//        public Wrapping(Node currentNode, Wrapping previousPath) {
//            this.currentNode = currentNode;
//            this.previousPath = previousPath;
//            noPrevPath = false;
//        }
//
//        public Wrapping(Node currentNode) {
//            this.currentNode = currentNode;
//            noPrevPath = true;
//        }
//
//        public void addPath(Wrapping path) {
//            this.previousPath = path;
//            noPrevPath = false;
//        }
//    }


    /**
     * pathOfNodesBFS: BFS Algorithm Implementation
     * @param startID
     * @param endID
     * @return path of nodes
     */
    public ArrayList pathOfNodesBFS(int startID, int endID) {

        ArrayList<Integer> queue = new ArrayList<Integer>();
        ArrayList<Integer> nodesToReset = new ArrayList<Integer>();

        int currentID = startID;

        GraphNode currentGNode = graph.getGraphNode(currentID);

        while (currentID != endID) {
            for (int i = 0; i < currentGNode.edgeCount(); i++) {

                Edge currentEdge = currentGNode.getEdge(i);
                int otherNodeID;
                GraphNode otherGNode;

                if (currentEdge.getStartNode() == currentID) { // check whether node is starting node or ending node in the
                    // edge
                    otherNodeID = currentEdge.getEndNode();
                } else {
                    otherNodeID = currentEdge.getStartNode();
                }

//                boolean otherVisited = false;
//                for (int j = 0; j < visited.size(); j++) {
//                    if (otherNode
//                            .getNodeID() == visited.get(j).getNodeID()) { // check if node has been visited
//                        otherVisited = true;
//                    }
//                }

                if (!currentGNode.isVisited()) { // if not visited, add to queue and add to wrapping queue
                    graph.getGraphNode(otherNodeID).setPrev(currentGNode);
                    queue.add(otherNodeID);
                }
            }

            currentGNode.setVisited(true);
            currentID = queue.remove(0);
            currentGNode = graph.getGraphNode(currentID);
        }

        ArrayList<Integer> path = getOrder(startID, endID);

        resetNodes(nodesToReset);

        return path;
    }


    /**
     * pathOfNodesAStar: A* Algorithm Implementation
     * @param startID
     * @param endID
     * @return path of nodes
     */
    public ArrayList pathOfNodesAStar(int startID, int endID) {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        ArrayList<Integer> nodesToReset = new ArrayList<Integer>();

        GraphNode endNode = graph.getGraphNode(endID);
        int endX = endNode.getXcoord();
        int endY = endNode.getYcoord();
        GraphNode currentNode = graph.getGraphNode(startID);
        int currentX = currentNode.getXcoord();
        int currentY = currentNode.getYcoord();

        //currentNode info
        currentNode.setgCost(0);
        currentNode.sethCost((int) (Math.hypot(endX-currentX, endX-currentX)));

        while(currentNode.getNodeID() != endID) {
            for(int i=0; i<currentNode.edgeCount(); i++){
                Edge currentEdge = currentNode.getEdge(i);
                int otherNodeID;
                GraphNode otherGNode;
                if (currentEdge.getStartNode() == currentNode.getNodeID()) { // check whether node is starting node or ending node in the
                    // edge
                    otherNodeID = currentEdge.getEndNode();
                } else {
                    otherNodeID = currentEdge.getStartNode();
                }
                otherGNode = graph.getGraphNode(otherNodeID);
                if(!otherGNode.isVisited()){
                    int gCost = currentNode.getgCost()+ (int) Math.hypot(currentX-otherGNode.getXcoord(),currentY-otherGNode.getYcoord());
                    int hCost = (int) Math.hypot(endX-otherGNode.getXcoord(),endY-otherGNode.getYcoord());
                    otherGNode.setgCost(gCost);
                    otherGNode.sethCost(hCost);
                    otherGNode.setPrev(currentNode);
                    insertIntoPQ(queue, otherNodeID); // Not implemented yet
                }

            }

            currentNode.setVisited(true);
            nodesToReset.add(currentNode.getNodeID());

            currentNode = graph.getGraphNode(queue.remove(0));
            currentX = currentNode.getXcoord();
            currentY = currentNode.getYcoord();

        }

        ArrayList<Integer> path = getOrder(startID, endID);

        resetNodes(nodesToReset);

        return path;
    }



    public void insertIntoPQ(ArrayList<Integer> pQ, int nodeID) {

        //do it

    }

    public ArrayList getOrder(int startID, int endID) {
        ArrayList<Integer> path = new ArrayList<Integer>();
        int currentID = endID;
        GraphNode currentGNode = graph.getGraphNode(currentID);
        while (currentID != startID) { // while the path still has a previous path
            path.add(0, currentID);
            currentGNode = currentGNode.getPrev();
            currentID = currentGNode.getNodeID();
        }
        path.add(0, startID);
        return path;
    }

    public void resetNodes(ArrayList<Integer> resetList) {
        for (int i = 0; i < resetList.size(); i++) {
            graph.getGraphNode(resetList.get(i)).reset();
        }
    }

}
