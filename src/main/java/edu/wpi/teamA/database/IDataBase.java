package edu.wpi.teamA.database;

public interface IDataBase {
  Integer nodeID = null;

  String longName = null;

  static void Import(String s) {};

  static void Export(String s) {};

  void Add();

  void Delete();

  void Update();

  // 1.get all
  // 2.update
  // 3.delete

  // app start: instantiate and import all csv
  // if csv already exists, drag it from the database

  // pass rare data to the DAO, create new object in the DAO

}
