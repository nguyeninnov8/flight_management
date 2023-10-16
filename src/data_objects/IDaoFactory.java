/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import utils.IValidation;

/**
 *
 * @author ASUS
 */
public interface IDaoFactory {
    IFlightDao flightDao();
    IValidation validator();
    IReservationDao reservationDao();
}
