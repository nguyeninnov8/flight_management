/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Reservation;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IReservationDao {
    List<Reservation> getAllReservation();
    Reservation getReservation(String id);
    boolean addReservation(Reservation reservation);
    boolean deleteReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    boolean checkReservationExist(String id);
    int countAllReservation();
    String generateNextReservationId();
}
