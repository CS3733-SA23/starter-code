package edu.wpi.teame.controllers;

public interface IRequestController {

  ServiceRequestData sendRequest();

  void cancelRequest();
}
