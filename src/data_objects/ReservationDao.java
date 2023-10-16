/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Reservation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ReservationDao implements IReservationDao{
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
}
