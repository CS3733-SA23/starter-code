package edu.wpi.teamname.controllers;

public class FlowerRequestController {
  String flowerType;
  int numOfFlowers;
  String recipientName;
  int roomNumber;
  String notes;

  FlowerRequestController(
      String flowerType, int numOfFlowers, String recipientName, int roomNumber, String notes) {
    this.flowerType = flowerType;
    this.numOfFlowers = numOfFlowers;
    this.recipientName = recipientName;
    this.roomNumber = roomNumber;
    this.notes = notes;
  }
}
