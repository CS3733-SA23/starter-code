package edu.wpi.teamA.entity;

import java.util.ArrayList;
import java.util.List;

public class FlowerDAOImpl implements FlowerDAO {

  List<FlowerEntity> flowers;

  public FlowerDAOImpl() {
    flowers = new ArrayList<FlowerEntity>();
  }

  @Override
  public void deleteFlower(FlowerEntity flower) {}

  // retrive list of students from the database
  @Override
  public List<FlowerEntity> getAllFlowers() {
    return flowers;
  }

  @Override
  public FlowerEntity getFlower() {
    return flowers.get(0);
  }

  @Override
  public void updateFlower(FlowerEntity flower) {}
}
