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
public class Admin extends Person{
    
    public Admin(String firstName, String lastName, String phoneNumber, String address, String email, int age, LocalDate dateOfBirth) {
        super(firstName, lastName, phoneNumber, address, email, age, dateOfBirth);
    }
    
}
