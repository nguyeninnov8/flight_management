/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.CrewMember;
import business_object.Flight;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.HandlingFile;

/**
 *
 * @author ASUS
 */
public class FlightDao implements IFlightDao{
    private final String FLIGHT_FILEPATH = "src\\flight.dat";
    private List<Flight> flightList;

    public FlightDao() {
        this.flightList = new ArrayList<>();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
//        Flight F1 = new Flight("F1234", "TN", "TPHCM", LocalDateTime.parse("10/10/2023 12:30", dateTimeFormatter), LocalDateTime.parse("10/10/2023 17:30", dateTimeFormatter));
//        Flight F2 = new Flight("F3544", "HN", "TPHCM", LocalDateTime.parse("20/10/2023 12:30", dateTimeFormatter), LocalDateTime.parse("20/10/2023 17:30", dateTimeFormatter));
//        flightList.add(F1);
//        flightList.add(F2);
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
        for (Flight flight : flightList) {
            LocalDate ld = flight.getDepartureTime().toLocalDate();
            if(flight.getDepartureCity().equalsIgnoreCase(departureLocation) && 
                    flight.getDestinationCity().equalsIgnoreCase(arrivalLocation) &&
                    ld.equals(flightDate)) {
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
        System.out.format("%4d %d %d %d %d %d\n", 1, 2, 3, 4, 5, 6);
        for (boolean[] row : seats) {
            System.out.print(String.format("%2c ", (char) ('A' + i)));
            for (boolean seat : row) {
                if(seat) {
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
    public boolean setValidSeat(Flight flight, String seat) {
       int row = Character.toUpperCase(seat.charAt(0)) - 65;
       int column = Integer.parseInt(seat.charAt(1)+"") - 1;
       boolean[][] seats = flight.getAvailableSeat();
       if(!seats[row][column]){
           System.err.println(seat + " has been taken!");
           return false;
       }
       flight.setUnavailable(row, column);
       getFlight(flight.getFlightNumber()).setUnavailable(row, column);
       return true;
    }

    @Override
    public boolean isCrewMemberExist(String memberId) {
        for(Flight flight: flightList){
            for(CrewMember member: flight.getCrewMembers()){
                if(member.getId().equals(memberId)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean saveToFile() {
        return new HandlingFile<Flight>().saveToFile(FLIGHT_FILEPATH, flightList);
    }

    @Override
    public boolean loadFromFile() {
        return new HandlingFile<Flight>().loadFromFile(FLIGHT_FILEPATH, flightList);
    }
}
