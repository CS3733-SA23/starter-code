package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NodeDAOTest {
    static NodeDAO dao;

    @BeforeAll
    static void setUp() throws Exception {
        dao = NodeDAO.createInstance("teamr", "teamr150", "node",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        dao.deleteNodes(null, null, null, null, null);
        dao.addNode(342, 3838,8989, "L2", "The second one");

    }

    @AfterAll
    static void tearDown() throws Exception {
        dao.deleteNodes(null, null, null, null, null);
    }

    @Test
    void createInstance() throws SQLException, ClassNotFoundException {
        NodeDAO dao2 = NodeDAO.createInstance("teamr", "teamr150", "node",
                "test", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        assertSame(dao, dao2);
    }

    @Test
    void getInstance() {
        NodeDAO dao2 = NodeDAO.getInstance();
        assertSame(dao, dao2);
    }

    @Test
    void addAndDeleteNode() throws Exception {
        dao.deleteNodes(null, null, null, null, null);
        Node node = new Node(343, 9323, 2938, "2", "other bud");
        dao.addNode(node.getNodeID(),node.getxCoord(),node.getyCoord(),node.getFloorNum(),node.getBuilding());
        Node node2 = dao.selectNodes(343, null, null, null, null).get(0);
        assertEquals(node.getNodeID(), node2.getNodeID());
        assertEquals(node.getxCoord(), node2.getxCoord());
        assertEquals(node.getyCoord(), node2.getyCoord());
        assertEquals(node.getFloorNum(), node2.getFloorNum());
        assertEquals(node.getBuilding(), node2.getBuilding());
        dao.deleteNodes(343, null, null, null, null);
        assertEquals(dao.selectNodes(343, null, null, null, null).size(), 0);
    }

    @Test
    void modifyNodeByID() throws SQLException, ClassNotFoundException, NotFoundException {
        dao.addNode(344, 100, 100, "L8", "temp");
        dao.modifyNodeByID(344, 0, 0, "L2", "The third one");
        ArrayList<Node> nodes = dao.selectNodes(null, null, null, null, "The third one");
        assertEquals(nodes.size(), 1);
        Node node = nodes.get(0);
        Node node2 = new Node(344, 0, 0, "L2", "The third one");
        assertTrue(Objects.equals(node.getNodeID(), node2.getNodeID()));
        assertTrue(Objects.equals(node.getxCoord(), node2.getxCoord()));
        assertTrue(Objects.equals(node.getyCoord(), node2.getyCoord()));
        assertTrue(node.getFloorNum().equals(node2.getFloorNum()));
        assertTrue(node.getBuilding().equals(node2.getBuilding()));
        dao.deleteNodes(344, null, null, null, null);
    }

    @Test
    void selectNodes() throws SQLException, ClassNotFoundException {
        dao.deleteNodes(null, null, null, null, null);
        dao.addNode(1, 0, 0, "5", "h");
        dao.addNode(2, 0, 0, "5", "");
        dao.addNode(3, 2, 0, "", "");
        dao.addNode(4, 1, 1, "", "");

        ArrayList<Node> nodes = dao.selectNodes(null, null, null, null, null);
        assertEquals(nodes.size(), 4);

        nodes = dao.selectNodes(null, 0, 0, "5", "h");
        assertEquals(nodes.size(), 1);

        nodes = dao.selectNodes(2, 0, 0, "5", "h");
        assertEquals(nodes.size(), 0);

        nodes = dao.selectNodes(2, 0, 0, "5", "");
        assertEquals(nodes.size(), 1);

        nodes = dao.selectNodes(3, null, null, null, null);
        assertEquals(nodes.size(), 1);
    }

    /*
    @Test
    void writeCSV() {

    }

    @Test
    void readCSV() {
    }
     */
}