package edu.wpi.teamA.entity;

public interface IConferenceDAO {
  public void updateConference(ConferenceEntity conference);

  public void addConference(ConferenceEntity conference);

  public void deleteConference(ConferenceEntity conference);
}
