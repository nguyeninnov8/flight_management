/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Flight;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class FlightDao implements IFlightDao{
    private List<Flight> flightList;

    public FlightDao() {
        this.flightList = new ArrayList<>();
    }
    
    @Override
    public List<Flight> getAll() {
        return flightList;
    }

    @Override
    public boolean addNewFlight(Flight flight) {
        return flightList.add(flight);
    }

    @Override
    public boolean deleteFlight(Flight flight) {
        return flightList.remove(flight);
    }

    @Override
    public boolean updateFlight(Flight flight) {
        Flight updatedFlight = getFlight(flight.getFlightNumber());
        if(updatedFlight != null) {
            updatedFlight = flight;
            return true;
        }
        return false;
    }

    @Override
    public Flight getFlight(String flightId) {
        for (Flight flight : flightList) {
            if(flight.getFlightNumber().equals(flightId))
                return flight;
        }
        return null;
    }
}
