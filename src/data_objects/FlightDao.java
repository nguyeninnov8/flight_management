/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Flight;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public boolean checkFightExist(String flightId) {
        return (getFlight(flightId) != null);
    }

    @Override
    public List<Flight> getFlightBaseOnDepartArriLocateDate(String departureLocation, String arrivalLocation, LocalDate flightDate) {
        List<Flight> resultList = new ArrayList<>();
        for (Flight flight : resultList) {
            LocalDateTime ld = flight.getDepartureTime().atStartOfDay();
            if(flight.getDepartureCity().equalsIgnoreCase(departureLocation) && 
                    flight.getDestinationCity().equalsIgnoreCase(arrivalLocation) &&
                    ld.equals(flightDate.atStartOfDay())) {
                resultList.add(flight);
            }
        }
        return resultList;
    }

    @Override
    public boolean checkEmptyList(List<Flight> list) {
        return list.isEmpty();
    }

    @Override
    public boolean checkFightExist(String flightId, List<Flight> list) {
        for (Flight flight : list) {
            if(flight.getFlightNumber().equals(flightId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showAllSeats(Flight flight) {
        boolean[][] seats = flight.getAvailableSeat();
        int i = 0;
        System.out.format("%2d %d %d %d %d %d", 1, 2, 3, 4, 5, 6);
        for (boolean[] row : seats) {
            System.out.print(String.format("%c", (char) ('A' + i)));
            for (boolean seat : row) {
                if(seat == true) {
                    System.out.print("O" + " ");
                } else {
                    System.out.print("X" + " ");
                }
            }
            i++;
            System.out.println("");
        }
    }

    @Override
    public boolean isValidSeat(Flight flight, String seat) {
       int row = Character.toUpperCase(seat.charAt(0)) - 97;
       int column = Integer.parseInt(seat.charAt(1)+"");
       boolean[][] seats = flight.getAvailableSeat();
       if(!seats[row][column]){
           return false;
       }
       
       return true;
    }
}
