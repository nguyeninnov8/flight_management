/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASUS
 */
public class Flight {
    private final int maxColumn = 6;
    private final int maxRow = 24;
    public static final String regex = "F\\d{4}";
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private LocalDate departureTime;
    private LocalDate arrivalTime;
    private boolean[][] availableSeat;

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalDate departureTime, LocalDate arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeat = new boolean[maxRow][maxColumn];
        initializeSeat(availableSeat);
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

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean[][] getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(boolean[][] availableSeat) {
        this.availableSeat = availableSeat;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String departureTimeStr = this.departureTime.format(dateTimeFormatter);
        String arrivalTImeStr = this.arrivalTime.format(dateTimeFormatter);
        return String.format("%6s|%11s|%11s|%8s|%8s", this.flightNumber, this.departureCity, this.destinationCity, departureTimeStr, arrivalTImeStr);
    }
}
