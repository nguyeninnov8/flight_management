
package data_objects;

import business_object.Flight;
import business_object.Reservation;
import java.util.ArrayList;
import java.util.List;
import utils.HandlingFile;

public class ReservationDao implements IReservationDao{
    private final String RESERVATION_FILEPATH = "src\\reservation.dat";
    List<Reservation> reservationList;

    public ReservationDao() {
        this.reservationList = new ArrayList<>();
    }

    @Override
    public List<Reservation> getAllReservation() {
        return reservationList;
    }

    @Override
    public Reservation getReservation(String id) {
        for (Reservation reservation : reservationList) {
            if(reservation.getId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public boolean addReservation(Reservation reservation) {
        return reservationList.add(reservation);
    }

    @Override
    public boolean deleteReservation(Reservation reservation) {
        return reservationList.remove(reservation);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        Reservation updatedReservation = getReservation(reservation.getId());
        if(updatedReservation != null) {
            updatedReservation = reservation;
            return true;
        }
        return false;
    }

    @Override
    public int countAllReservation() {
        return reservationList.size() + 1;
    }
    
    @Override
     public String generateNextReservationId() {
        int counter = countAllReservation();
        return "R" + String.format("%04d", counter);
    }

    @Override
    public boolean checkReservationExist(String id) {
        return (getReservation(id) != null);
    }

    @Override
    public boolean saveToFile() {
        return new HandlingFile<Reservation>().saveToFile(RESERVATION_FILEPATH, reservationList);
    }

    @Override
    public boolean loadFromFile() {
        return new HandlingFile<Reservation>().loadFromFile(RESERVATION_FILEPATH, reservationList);
    }

    @Override
    public boolean setValidSeat(Flight flight, String seat) {
       int row = Character.toUpperCase(seat.charAt(0)) - 65;
       int column = Integer.parseInt(seat.charAt(1)+"") - 1;
       boolean[][] seats = flight.getAvailableSeat();
       if(!seats[row][column]){
           return false;
       }
       for(Reservation temp: reservationList){
           Flight tempFlight = temp.getReservedFlight();
           if(tempFlight.getFlightNumber().equals(flight.getFlightNumber())){
               tempFlight.setUnavailable(row, column);
           }
       }
       return true;
    }
}
