/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

/**
 *
 * @author ASUS
 */
public class Flight implements Serializable{
    private final int maxColumn = 6;
    private final int maxRow = 24;
    public static final String regex = "F\\d{4}";
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean[][] availableSeat;
    private List<CrewMember> crewMembers;

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeat = new boolean[maxRow][maxColumn];
        this.crewMembers = new ArrayList<>();
        initializeSeat(availableSeat);
    }

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalDateTime departureTime, LocalDateTime arrivalTime, boolean[][] availableSeat, List<CrewMember> crewMembers) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeat = availableSeat;
        this.crewMembers = crewMembers;
    }    
    
    private void initializeSeat(boolean[][] seats) {
        for(int i = 0; i < seats.length; i++) {
            for(int j = 0; j < seats[i].length; j++) {
                seats[i][j] = true;
            }
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean[][] getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(boolean[][] availableSeat) {
        this.availableSeat = availableSeat;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
    
    public boolean isCrewMemberExist(String id){
        for(CrewMember member: crewMembers){
            if(member.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    
    public boolean removeCrew(CrewMember removedMember){
        return crewMembers.remove(removedMember);
    }
    
    public void setUnavailable(int row, int column){
        this.availableSeat[row][column] = false;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String departureTimeStr = this.departureTime.format(dateTimeFormatter);
        String arrivalTImeStr = this.arrivalTime.format(dateTimeFormatter);
        return String.format("%20s|%20s|%20s|%20s|%20s", this.flightNumber, this.departureCity, this.destinationCity, departureTimeStr, arrivalTImeStr);
    }

    public static Comparator compareDate = new Comparator(){
        @Override
        public int compare(Object o1, Object o2) {
            Flight flight1 = (Flight) o1;
            Flight flight2 = (Flight) o2;
            if(flight1.getDepartureTime().isAfter(flight2.getDepartureTime())){
                return 1;
            } else {
                return -1;
            }
        }
    };
}
