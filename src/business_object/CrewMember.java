/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASUS
 */
public class CrewMember extends Person implements Serializable{
    private String id;
    private RoleMember role;
    private boolean isAvailable;
    public static final String regex = "M\\d{4}";

    public CrewMember(String id, RoleMember role, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, phoneNumber, address, dateOfBirth);
        this.id = id;
        this.role = role;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoleMember getRole() {
        return role;
    }

    public void setRole(RoleMember role) {
        this.role = role;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
    

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%12s|%12s|%12s|%12s|%12s|%25s|%12s",
                this.id, this.role, this.getFirstName(), this.getLastName(), this.getPhoneNumber(), this.getAddress(), this.getDateOfBirth().format(dateTimeFormatter));
    }
    
    
}
