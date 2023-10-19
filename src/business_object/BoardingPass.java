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
    private String seat;

    public BoardingPass(Reservation reservation, String seat) {
        this.reservation = reservation;
        this.seat = seat;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "BoardingPass{" + "reservation=" + reservation + ", seat=" + seat + '}';
    }
}
