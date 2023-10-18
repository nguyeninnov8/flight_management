/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_object.CrewMember;
import business_object.Flight;
import business_object.RoleMember;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IService {
     void addNewFlight();
     void passengerReservation();
     void showAllFlight();
     List<Flight> showRequiredFlights(String departureLocation, String arrivalLocation, LocalDate date);
     void checkIn();
     // Crew management repository
     void addCrewMember(RoleMember role);
     void showCrewMembers(List<CrewMember> list);
     void showAllCrewMembers();
     void deleteCrewMember();
     // Crew assignment repository
     Flight inputFlight();
     void assignCrewToFlight();
     void showCrewOfFlight();
     void removeCrewFromFlight();
     // Handling file repository
     void saveAll();
     void loadAll();
}
