/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Flight;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IFlightDao{
    List<Flight> getAll();
    boolean addNewFlight(Flight flight);
    boolean deleteFlight(Flight flight);
    boolean updateFlight(Flight flight);
    Flight getFlight(String flightId);
}
