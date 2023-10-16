/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.Passenger;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IPassengerDao {
    List<Passenger> getAllPassengers();
    boolean addPassenger(Passenger passenger);
    boolean deletePassenger(Passenger passenger);
    boolean updatePassenger(Passenger passenger);
}
