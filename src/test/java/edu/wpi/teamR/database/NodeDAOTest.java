package edu.wpi.teamR.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class NodeDAOTest {
    static NodeDAO dao;

    @BeforeAll
    static void setUp() throws Exception {
        dao = NodeDAO.createInstance("teamr", "teamr150", "node",
                "prototype2", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        dao.addNode(342, 3838,8989, "L2", "The second one");

    }

    @AfterAll
    static void tearDown() throws Exception {
        dao.deleteNodes(342, null, null, null, null);
    }

    @Test
    void createInstance() {
        NodeDAO dao2 = NodeDAO.createInstance("teamr", "teamr150", "node",
                "prototype2", "jdbc:postgresql://database.cs.wpi.edu:5432/teamrdb");
        assertSame(dao, dao2);
    }

    @Test
    void getInstance() {
        NodeDAO dao2 = NodeDAO.getInstance();
        assertSame(dao, dao2);
    }

    @Test
    void addAndDeleteNode() throws Exception {
        Node node = new Node(343, 9323, 2938, "2", "other bud");
        dao.addNode(node.getNodeID(),node.getxCoord(),node.getyCoord(),node.getFloorNum(),node.getBuilding());
        Node node2 = dao.selectNodes(343, null, null, null, null).get(0);
        assertSame(node, node2);
        dao.deleteNodes(343, null, null, null, null);
        assertEquals(dao.selectNodes(343, null, null, null, null).size(), 0);
    }

    @Test
    void modifyNodeByID() {

    }

    @Test
    void selectNodes() {
    }

    @Test
    void writeCSV() {
    }

    @Test
    void readCSV() {
    }
}