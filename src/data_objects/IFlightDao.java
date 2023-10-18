/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Flight;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IFlightDao {
    List<Flight> getAll();
    boolean checkEmptyList(List<Flight> list);
    boolean addNewFlight(Flight flight);
    boolean deleteFlight(Flight flight);
    boolean updateFlight(Flight flight);
    Flight getFlight(String flightId);
    boolean checkFightExist(String flightId);
    boolean checkFightExist(String flightId, List<Flight> list);
    List<Flight> getFlightBaseOnDepartArriLocateDate(String departureLocation, String arrivalLocation, LocalDate flightDate);
    void showAllSeats(Flight flight);
    boolean setValidSeat(Flight flight, String seat);
    boolean isCrewMemberExist(String memberId);
    // handling file
    boolean saveToFile();
    boolean loadFromFile();
}
