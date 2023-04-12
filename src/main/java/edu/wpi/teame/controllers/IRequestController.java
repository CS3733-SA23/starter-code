package edu.wpi.teame.controllers;

import edu.wpi.teame.entities.ServiceRequestData;

public interface IRequestController {

  ServiceRequestData sendRequest();

  void cancelRequest();
}
