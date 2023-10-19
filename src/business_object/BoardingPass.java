/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class BoardingPass implements Serializable{
    private Reservation reservation;
    private Passenger passenger;
    private String seat;
    private Flight flight;

    public BoardingPass(Reservation reservation,Passenger passenger, String seat, Flight flight) {
        this.reservation = reservation;
        this.passenger = passenger;
        this.seat = seat;
        this.flight = flight;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public String toString() {
        return "BoardingPass{" + "reservation=" + reservation + ", passenger=" + passenger + ", seat=" + seat + ", flight=" + flight + '}';
    }
}
