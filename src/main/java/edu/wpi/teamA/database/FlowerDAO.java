package edu.wpi.teamA.database;

import java.util.ArrayList;

public class FlowerDAO implements IDataBase, IFDRDAO {

  ArrayList<FlowerEntity> allFlowers = new ArrayList<FlowerEntity>();

  @Override
  public void Update() {};

  @Override
  public void Import(String filePath) {}

  @Override
  public void Export(String filePath) {}

  @Override
  public void Add() {}

  public void Delete() {}
}
