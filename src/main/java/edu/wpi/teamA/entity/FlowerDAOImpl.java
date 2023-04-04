package edu.wpi.teamA.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlowerDAOImpl implements IFlowerDAO {
  ArrayList<FlowerEntity> flowerArray = new ArrayList<>();
  Connection flowerConnection; // need connection to server

  public FlowerDAOImpl() {}

  @Override
  public void addFlower(FlowerEntity flower) {
    /** Insert new node object to the existing node table */
    try {
      String name = flower.getName();
      int room = flower.getRoom();
      Date date = flower.getDate();
      int time = flower.getTime();
      String type = flower.getFlowerType();
      String comment = flower.getComment();
      String status = flower.getStatus();

      PreparedStatement ps =
          flowerConnection.prepareStatement(
              "INSERT INTO Prototype2_schema.\"flower\" VALUES (?, ?, ?, ?, ?, ?, ?)");
      ps.setString(1, name);
      ps.setInt(2, room);
      ps.setDate(3, date);
      ps.setInt(4, time);
      ps.setString(5, type);
      ps.setString(6, comment);
      ps.setString(7, status);
      ps.executeUpdate();

      flowerArray.add(new FlowerEntity(name, room, date, time, type, comment));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteFlower(FlowerEntity flower) {
    try {
      PreparedStatement ps =
          flowerConnection.prepareStatement(
              "DELETE FROM Prototype2_schema.\"flower\" WHERE name = ?");
      ps.setString(1, flower.getName());
      ps.executeUpdate();

      flowerArray.removeIf(flowerEntity -> flowerEntity.getName().equals(flower.getName()));

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateFlower(FlowerEntity flower) {}
}
