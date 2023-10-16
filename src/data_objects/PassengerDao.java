/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Passenger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PassengerDao implements IPassengerDao{
    List<Passenger> passengerList;
    
    public PassengerDao() {
        passengerList = new ArrayList<>();
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerList;
    }

    @Override
    public boolean addPassenger(Passenger passenger) {
        return passengerList.add(passenger);
    }

    @Override
    public boolean deletePassenger(Passenger passenger) {
        return passengerList.remove(passenger);
    }

    @Override
    public boolean updatePassenger(Passenger passenger) {
        int updatedPassengerIndex = passengerList.indexOf(passenger);
        if(updatedPassengerIndex != -1) {
            Passenger updatedPassenger = passengerList.get(updatedPassengerIndex);
            updatedPassenger = passenger;
        } 
        return false;
    }
}
