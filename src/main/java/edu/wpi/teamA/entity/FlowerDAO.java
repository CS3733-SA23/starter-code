package edu.wpi.teamA.entity;

import java.util.List;

public interface FlowerDAO {
  public List<FlowerEntity> getAllFlowers();

  public FlowerEntity getFlower();

  public void updateFlower(FlowerEntity flower);

  public void deleteFlower(FlowerEntity flower);
}
