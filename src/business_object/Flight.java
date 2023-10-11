/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_object;

import java.time.LocalTime;

/**
 *
 * @author ASUS
 */
public class Flight {
    private final int maxColumn = 6;
    private final int maxRow = 50;
    static final String regex = "F\\d{4}";
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private boolean[][] availableSeat;

    public Flight(String flightNumber, String departureCity, String destinationCity, LocalTime departureTime, LocalTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeat = new boolean[maxRow][maxColumn];
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

    public boolean[][] getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(boolean[][] availableSeat) {
        this.availableSeat = availableSeat;
    }
}
