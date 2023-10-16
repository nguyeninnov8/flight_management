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
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate flightDate;
    private String[][] availableSeat;

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalTime departureTime, LocalTime arrivalTime, LocalDate flightDate) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightDate = flightDate;
        this.availableSeat = new String[maxRow][maxColumn];
        initializeSeat(availableSeat);
    }
    
    private void initializeSeat(String[][] seats) {
        for(int i = 0; i < seats.length; i++) {
            char rowChar = (char) ('A' + i);
            for(int j = 0; j < seats[i].length; j++) {
                seats[i][j] = String.valueOf(rowChar) + (j + 1);
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

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public String[][] getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(String[][] availableSeat) {
        this.availableSeat = availableSeat;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String departureTimeStr = this.departureTime.format(timeFormatter);
        String arrivalTImeStr = this.arrivalTime.format(timeFormatter);
        String fligtDateStr = this.flightDate.format(dateFormatter);
        return String.format("%6s|%11s|%11s|%8s|%8s|%8s", this.flightNumber, this.departureCity, this.destinationCity, departureTimeStr, arrivalTImeStr, fligtDateStr);
    }
}
