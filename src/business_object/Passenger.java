/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class Passenger extends Person{

    public Passenger(String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, phoneNumber, address, dateOfBirth);
    }
}
