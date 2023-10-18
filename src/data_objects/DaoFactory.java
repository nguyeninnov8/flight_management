/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import utils.IValidation;
import utils.Validation;

/**
 *
 * @author ASUS
 */
public class DaoFactory implements IDaoFactory{
    
    @Override
    public IFlightDao flightDao() {
        return new FlightDao();
    }

    @Override
    public IValidation validator() {
        return new Validation();
    }

    @Override
    public IReservationDao reservationDao() {
        return new ReservationDao();
    }

    @Override
    public ICrewMemberDao crewMemberDao() {
        return new CrewMemberDao();
    }

    @Override
    public IPassengerDao passengerDao() {
        return new PassengerDao();
    }
    
}
