
package data_objects;

import business_object.Flight;
import business_object.Reservation;
import java.util.List;

public interface IReservationDao {
    List<Reservation> getAllReservation();
    Reservation getReservation(String id);
    boolean addReservation(Reservation reservation);
    boolean deleteReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    boolean checkReservationExist(String id);
    int countAllReservation();
    String generateNextReservationId();
    boolean setValidSeat(Flight flight, String seat);
    // handling file
    boolean saveToFile();
    boolean loadFromFile();
}
