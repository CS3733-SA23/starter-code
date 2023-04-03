package edu.wpi.teamc.map;

public class GraphEdge extends Edge implements Comparable<GraphEdge> {
    private double weight;
    private double heuristic;

    public GraphEdge(String id, Node startNode, Node endNode) {
        super(id, startNode, endNode);
        this.weight =
                Math.hypot(
                        startNode.getXCoord() - endNode.getXCoord(),
                        startNode.getYCoord() - endNode.getYCoord());
    }

    /**
     * Use manhattan distance to set heuristic value
     *
     * @param targetNode Target destination
     */
    public void setHeuristic(GraphNode targetNode) {
        this.heuristic =
                Math.hypot(
                        getStartNode().getXCoord() - targetNode.getXCoord(),
                        getEndNode().getYCoord() - targetNode.getYCoord());
    }

    @Override
    public int compareTo(GraphEdge edge) {
        return Double.compare(weight + heuristic, edge.weight + edge.heuristic);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            String[] id = ((GraphEdge) o).getId().split("_");
            String reverseID = id[1] + "_" + id[0];

            return reverseID.equals(this.getId());
        }
    }
}
