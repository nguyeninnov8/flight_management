/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_object.BoardingPass;
import business_object.Flight;
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
}
