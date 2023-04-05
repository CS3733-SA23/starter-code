package edu.wpi.teamA.database.DAOImps;

import edu.wpi.teamA.database.Interfaces.IFDRDAO;
import edu.wpi.teamA.database.ORMclasses.FlowerEntity;
import java.util.ArrayList;

public class FlowerDAO implements IDataBase, IFDRDAO {

  ArrayList<FlowerEntity> allFlowers = new ArrayList<FlowerEntity>();

  @Override
  public void Update() {};

  public static void Import(String filePath) {}

  public static void Export(String filePath) {}

  @Override
  public void Add() {}

  public void Delete() {}
}
