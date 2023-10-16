/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_object.BoardingPass;
import business_object.Flight;
import business_object.Passenger;
import business_object.Reservation;
import data_objects.DaoFactory;
import data_objects.IDaoFactory;
import data_objects.IFlightDao;
import data_objects.IReservationDao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.IValidation;

/**
 *
 * @author ASUS
 */
public class Service implements IService {

    static final IDaoFactory factoryDao = new DaoFactory();
    static final IFlightDao flightDao = factoryDao.flightDao();
    static final IValidation validator = factoryDao.validator();
    static final IReservationDao rervationDao = factoryDao.reservationDao();

    @Override
    public void addNewFlight() {
        while (true) {
            String flightNumber = validator.inputString("Input Flight Number: ", Flight.regex);

            if (flightDao.getFlight(flightNumber) != null) {
                System.err.println("This flight number has exist. Please input another flight number");
                continue;
            }
            break;
        }
        String depatureCity = validator.inputString("Input Departure City: ");
        String destinationCity = validator.inputString("Input Destination City: ");
        LocalDate departureTime = validator.inputDepatureTime("Input departure time in HH:mm:ss format (e.g., 12:30): ");
        LocalDate arrivalTime = validator.inputArrivalTime("Input arrival time in HH:mm:ss format (e.g., 12:30): ", departureTime);
        Flight toAddFlight = new Flight(depatureCity, depatureCity, destinationCity, departureTime, arrivalTime);
        if (flightDao.addNewFlight(toAddFlight)) {
            System.out.println("Flight added successfully!");
        } else {
            System.err.println("Some error has occured. Please check again!");
        }
    }

    @Override
    public void passengerReservation() {
        String requiredDepCity = validator.inputString("Input Departure City: ");
        String requiredArriCity = validator.inputString("Input Arrival City: ");
        LocalDate requireDate = validator.inputDate("Input date: ");
        List<Flight> showList = showRequiredFlights(requiredDepCity, requiredArriCity, requireDate);
        if(showList == null) return;
        String reservedFlightNumber;
        
        while (true) {
            reservedFlightNumber = validator.inputString("Please enter flight number you want to resevere: ");
            if (!flightDao.checkFightExist(reservedFlightNumber, showList)) {
                System.err.println("This flight number does not exist.\n"
                        + "Please enter again");
                continue;
            }
            break;
        }
        
        String passengerFirstName = validator.inputString("Input your first name: ");
        String passengerLastName = validator.inputString("Input your last name: ");
        String passengerPhoneNumber = validator.inputString("Input your phone number: ");
        String passengerAddress = validator.inputString("Input your address: ");
        LocalDate passengerDob = validator.inputDate("Input Date of Birth: ");
        
        Passenger toAddPassenger = new Passenger(passengerFirstName, passengerLastName, passengerPhoneNumber, passengerAddress, passengerDob);
        
        Reservation toAddReservation = new Reservation(rervationDao.generateNextReservationId(), toAddPassenger, flightDao.getFlight(reservedFlightNumber));
        
        rervationDao.addReservation(toAddReservation);
    }

    @Override
    public void showAllFlight() {
        System.out.println("/t------Flight-------/t");
        System.out.println(String.format("%12s|%12s|%12s|%12s|%12s|12s",
                "Flight Number", "Departure City", "Destination City", "Depature Time", "Arrival Time", "Date"));
        for (Flight flight : flightDao.getAll()) {
            System.out.println(flight);
        }
        System.out.println("-------------------");
    }

    @Override
    public List<Flight> showRequiredFlights(String departureLocation, String arrivalLocation, LocalDate date) {
        List<Flight> resultList = flightDao.getFlightBaseOnDepartArriLocateDate(departureLocation, arrivalLocation, date);
        
        if(resultList == null) {
            System.out.println("There is no flight that meet your requirement!");
            return null;
        }
        System.out.println("/t------Flight-------/t");
        System.out.println(String.format("%12s|%12s|%12s|%12s|%12s|12s",
                "Flight Number", "Departure City", "Destination City", "Depature Time", "Arrival Time", "Date"));
        for (Flight flight : resultList) {
            System.out.println(flight);
        }
        System.out.println("-------------------");
        return resultList;
    }

    @Override
    public void checkIn() {
        String reservationId, choosedSeat;
        while (true) {
            reservationId =  validator.inputReservation("Please input reseravation id: ", "R\\d{4}");
            if(rervationDao.checkReservationExist(reservationId)) {
                System.err.println("This reservation id does not exist!\n"
                        + "Please check again!");
                continue;
            }
            break;
        }
        
        flightDao.showAllSeats(rervationDao.getReservation(reservationId).getReservedFlight());
        while (true) {
           choosedSeat = validator.inputString("Please choose your seat (e.g., A1, A2, A3,...): "); 
           if(!flightDao.isValidSeat(rervationDao.getReservation(reservationId).getReservedFlight(), choosedSeat)) {
               System.err.println("Please input valid seat!");
               continue;
           }
           break;
        }
        
        BoardingPass boardingPass = new BoardingPass(rervationDao.getReservation(reservationId).getReservedPassenger(), choosedSeat, rervationDao.getReservation(reservationId).getReservedFlight());
    }

 
    private void viewBoardingPass(BoardingPass boardingPass) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("-------------------------------------");
        System.out.println("|            Boarding Pass          |");
        System.out.println("-------------------------------------");
        System.out.format("|NAME: %s                            |\n", (boardingPass.getPassenger().getFirstName() + " " + boardingPass.getPassenger().getLastName()));
        System.out.format("|FROM: %s                            |\n", boardingPass.getFlight().getDepartureCity());
        System.out.format("|TO: %s                              |\n", boardingPass.getFlight().getDestinationCity());
        System.out.format("|TIME:                               |\n");
        System.out.format("|      DEPARTURE TIME: %S            |\n", boardingPass.getFlight().getDepartureTime().format(dateTimeFormatter));
        System.out.format("|      ARRIVAL TIME: %S              |\n", boardingPass.getFlight().getArrivalTime().format(dateTimeFormatter));

    }
 }

