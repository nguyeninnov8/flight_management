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
public class Reservation implements Serializable{
    private String id;
    private Passenger reservedPassenger;
    private Flight reservedFlight;

    public Reservation(String id, Passenger reservedPassenger, Flight reservedFlight) {
        this.id = id;
        this.reservedPassenger = reservedPassenger;
        this.reservedFlight = reservedFlight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Passenger getReservedPassenger() {
        return reservedPassenger;
    }

    public void setReservedPassenger(Passenger reservedPassenger) {
        this.reservedPassenger = reservedPassenger;
    }

    public Flight getReservedFlight() {
        return reservedFlight;
    }

    public void setReservedFlight(Flight reservedFlight) {
        this.reservedFlight = reservedFlight;
    }
}
