/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action_service;

import business_object.BoardingPass;
import business_object.CrewMember;
import business_object.Flight;
import business_object.Passenger;
import business_object.Reservation;
import business_object.RoleMember;
import data_objects.DaoFactory;
import data_objects.IBoardingPassDao;
import data_objects.ICrewMemberDao;
import data_objects.IDaoFactory;
import data_objects.IFlightDao;
import data_objects.IPassengerDao;
import data_objects.IReservationDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utils.IValidation;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ASUS
 */
public class Service implements IService {

    static final IDaoFactory factoryDao = new DaoFactory();
    static final IFlightDao flightDao = factoryDao.flightDao();
    static final IValidation validator = factoryDao.validator();
    static final IReservationDao rervationDao = factoryDao.reservationDao();
    static final ICrewMemberDao crewMemberDao = factoryDao.crewMemberDao();
    static final IPassengerDao passengerDao = factoryDao.passengerDao();
    static final IBoardingPassDao boardingPassDao = factoryDao.boardingPassDao();

    @Override
    public void addNewFlight() {
        String flightNumber;
        while (true) {
            flightNumber = validator.inputStringWithRegex("Please input correct format (Fxyzt with xyzt is a number)", "Input Flight Number: ", Flight.regex);

            if (flightDao.getFlight(flightNumber) != null) {
                System.err.println("This flight number has exist. Please input another flight number");
                continue;
            }
            break;
        }
        String depatureCity = validator.inputString("Input Departure City: ");
        String destinationCity = validator.inputString("Input Destination City: ");
        LocalDateTime departureTime = validator.inputDepatureTime("Input departure time in dd/MM/yyyy HH:mm format (e.g., 10/10/2010 12:30): ");
        LocalDateTime arrivalTime = validator.inputArrivalTime("Input arrival time in dd/MM/yyyy HH:mm format (e.g., 10/10/2010 12:30): ", departureTime);
        Flight toAddFlight = new Flight(flightNumber, depatureCity, destinationCity, departureTime, arrivalTime);
        if (flightDao.addNewFlight(toAddFlight)) {
            System.out.println("Flight added successfully!");
            showAllFlight();
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
        if (showList == null) {
            return;
        }
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
        String phoneNumber = validator.inputStringWithRegex("Please input correct format (10 digits)", "Input phone number: ", "^0\\d{9}$");
        String passengerAddress = validator.inputString("Input your address: ");
        LocalDate passengerDob = validator.inputDate("Input Date of Birth (format dd/MM/yyyy): ");

        Passenger toAddPassenger = new Passenger(passengerFirstName, passengerLastName, phoneNumber, passengerAddress, passengerDob);
        passengerDao.addPassenger(toAddPassenger);
        String reservationID = rervationDao.generateNextReservationId();
        Reservation toAddReservation = new Reservation(reservationID, toAddPassenger, flightDao.getFlight(reservedFlightNumber));
        System.out.println("Your reservation ID: " + reservationID);

        rervationDao.addReservation(toAddReservation);
    }

    @Override
    public void showAllFlight() {
        System.out.println("------Flight-------");
        System.out.println(String.format("%20s|%20s|%20s|%20s|%20s",
                "Flight Number", "Departure City", "Destination City", "Depature Time", "Arrival Time"));
        for (Flight flight : flightDao.getAll()) {
            System.out.println(flight);
        }
        System.out.println("-------------------");
    }

    @Override
    public List<Flight> showRequiredFlights(String departureLocation, String arrivalLocation, LocalDate date) {
        List<Flight> resultList = flightDao.getFlightBaseOnDepartArriLocateDate(departureLocation, arrivalLocation, date);

        if (resultList.isEmpty()) {
            System.out.println("There is no flight that meet your requirement!");
            return null;
        }
        System.out.println("------Flight-------");
        System.out.println(String.format("%20s|%20s|%20s|%20s|%20s",
                "Flight Number", "Departure City", "Destination City", "Depature Time", "Arrival Time"));
        for (Flight flight : resultList) {
            System.out.println(flight);
        }
        System.out.println("-------------------");
        return resultList;
    }

    @Override
    public void checkIn() {
        // check reservationId
        String reservationId, choosedSeat;
        while (true) {
            reservationId = validator.inputStringWithRegex("Please input correct format (Rxyzt with xyzt is a number)", "Please input reseravation id: ", "R\\d{4}");
            if (!rervationDao.checkReservationExist(reservationId)) {
                System.err.println("This reservation id does not exist!\n"
                        + "Please check again!");
                continue;
            }
            if(boardingPassDao.isExistReservationId(reservationId)) {
                System.err.println("This ReservationId has used before!");
                continue;
            }
            break;
        }
        Flight reserverdFlight = rervationDao.getReservation(reservationId).getReservedFlight();
        // show all seats
        flightDao.showAllSeats(reserverdFlight);
        // choose seat
        while (true) {
            choosedSeat = validator.inputStringWithRegex("Please input correct format (e.g., A1, A2, A3)", "Please choose your seat (e.g., A1, A2, A3,...): ", "^[a-zA-Z][1-6]$");
            if (!rervationDao.setValidSeat(reserverdFlight, choosedSeat)) {
                System.err.println(choosedSeat + " has been taken! Please input valid seat!");
                continue;
            }
            break;
        }
        flightDao.setValidSeat(reserverdFlight, choosedSeat);
        Reservation boardedReservation = rervationDao.getReservation(reservationId);
        BoardingPass boardingPass = new BoardingPass(boardedReservation, choosedSeat);
        if(boardingPassDao.addBoardingPass(boardingPass)) {
            System.out.println("Created BoardingPass Successfully!");
        } else {
            System.err.println("Created BoardingPass Fail!");
        }
        viewBoardingPass(boardingPass);
    }

    private void viewBoardingPass(BoardingPass boardingPass) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Flight flight = boardingPass.getReservation().getReservedFlight();
        Passenger passenger = boardingPass.getReservation().getReservedPassenger();
        System.out.println("-------------------------------------");
        System.out.println("|            Boarding Pass           |");
        System.out.println("-------------------------------------");
        System.out.format("|NAME: %-30s|\n", (passenger.getFirstName() + " " + passenger.getLastName()));
        System.out.format("|FROM: %-30s|\n", flight.getDepartureCity());
        System.out.format("|TO: %-32s|\n", flight.getDestinationCity());
        System.out.format("|TIME                                |\n");
        System.out.format("| DEPARTURE TIME: %-19s|\n", flight.getDepartureTime().format(dateTimeFormatter));
        System.out.format("| ARRIVAL TIME: %-21s|\n", flight.getArrivalTime().format(dateTimeFormatter));
        System.out.format("|FLIGHT: %-28s|\n", flight.getFlightNumber());
        System.out.format("|SEAT: %-30s|\n", boardingPass.getSeat());
        System.out.println("|------------------------------------|");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCrewMember(RoleMember role) {
        String crewMemberId;
        while (true) {
            crewMemberId = validator.inputStringWithRegex("Please input correct format (Mxyzt with xyzt is a number)", "Input Crew Number: ", CrewMember.regex);

            if (crewMemberDao.getCrewMember(crewMemberId) != null) {
                System.err.println("This crew member has exist. Please input another crew member number");
                continue;
            }
            break;
        }
        String firstName = validator.inputString("Input first name: ");
        String lastName = validator.inputString("Input last name: ");
        String phoneNumber = validator.inputStringWithRegex("Please input correct format (10 digits)", "Input phone number: ", "^0\\d{9}$");
        String address = validator.inputString("Input address: ");
        LocalDate dateOfBirth = validator.inputDate("Input date of birth (format dd/MM/yyyy): ");
        CrewMember crewMember = new CrewMember(crewMemberId.toUpperCase(), role, firstName, lastName, phoneNumber, address, dateOfBirth);
        if (crewMemberDao.addCrewMember(crewMember)) {
            System.out.println("Crew member added successfully!");
        } else {
            System.err.println("Some error has occured. Please check again!");
        }
    }

    @Override
    public void showCrewMembers(List<CrewMember> list) {
        System.out.println("------Crew Members-------");
        System.out.println(String.format("%20s|%20s|%20s|%20s|%20s|%20s|%20s",

                "Id", "Role", "First name", "Last name", "Phone number", "Address", "Date of birth"));
        for (CrewMember member : list) {
            System.out.println(member);
        }
        System.out.println("-------------------");
    }
    
    @Override
    public void showAllCrewMembers() {
        showCrewMembers(crewMemberDao.getAll());
    }

    @Override
    public void deleteCrewMember() {
        String crewMemberId;
        while (true) {
            crewMemberId = validator.inputStringWithRegex("Please input correct format (Mxyzt with xyzt is a number)", "Input Crew Number (uppercased): ", CrewMember.regex);

            if (crewMemberDao.getCrewMember(crewMemberId) == null) {
                System.err.println("This crew member hasn't exist. Please input another crew member number");
                continue;
            }
            if (validator.checkYesOrNo("Confirm delete (Y/N)?")) {
                CrewMember deletedMember = crewMemberDao.getCrewMember(crewMemberId);
                if (deletedMember.isIsAvailable()) {
                    crewMemberDao.deleteCrewMember(deletedMember);
                    System.out.println("Successfully delete: " + deletedMember);
                } else {
                    System.err.println("Fail to delete because member has been assigned to flight: " + deletedMember);
                }
            }
            break;
        }
    }
    
    @Override
    public Flight inputFlight() {
        // Choose a flight
        Flight assignedFlight;
        while (true) {
            String flightNumber = validator.inputStringWithRegex("Please input correct format (Fxyzt with xyzt is a number)", "Input Flight Number: ", Flight.regex);
            assignedFlight = flightDao.getFlight(flightNumber);
            if (assignedFlight == null) {
                System.err.println("This flight hasn't exist. Please input another flight number");
                continue;
            }
            break;
        }
        return assignedFlight;
    }

    @Override
    public void assignCrewToFlight() {
        // Choose a flight
        Flight assignedFlight = inputFlight();
        // show available crew members
        showCrewMembers(crewMemberDao.getAvailableMembers());
        // add one or many crew members
        do {
            CrewMember member;
            while (true) {
                String crewMemberId = validator.inputStringWithRegex("Please input correct format (Mxyzt with xyzt is a number)", "Input Crew Number: ", CrewMember.regex);
                member = crewMemberDao.getAvailabeMember(crewMemberId);
                if (member == null) {
                    System.err.println("This crew member hasn't exist in available crew members. Please input another crew member number");
                    continue;
                }
                break;
            }
            // set available to False
            member.setIsAvailable(false);
            assignedFlight.getCrewMembers().add(member);
            System.out.println("Successfully assign!");

        } while (validator.checkYesOrNo("Continue to add crew member (Y/N)?"));
    }

    @Override
    public void showCrewOfFlight() {
        Flight flight = inputFlight();
        showCrewMembers(flight.getCrewMembers());
    }
    
    @Override
    public void removeCrewFromFlight() {
        // Choose a flight
        Flight removedFlight = inputFlight();
        // Show crew members' flight
        showCrewMembers(removedFlight.getCrewMembers());
        // remove one or many crew members
        do {
            CrewMember member;
            while (true) {
                String crewMemberId = validator.inputStringWithRegex("Please input correct format (Mxyzt with xyzt is a number)", "Input Crew Number: ", CrewMember.regex);
                member = crewMemberDao.getCrewMember(crewMemberId);
                // check exist in crew member database
                if (member == null) {
                    System.err.println("This crew member hasn't exist in database. Please input another crew member number");
                    continue;
                }
                // check exist in flight's crew
                if(!removedFlight.isCrewMemberExist(crewMemberId)){
                    System.err.println("This crew member hasn't exist in this flight. Please input another crew member number");
                    continue;
                }
                break;
            }
            // remove from Flight
            if (validator.checkYesOrNo("Confirm delete (Y/N)?")) {
                member.setIsAvailable(true);
                if(removedFlight.removeCrew(member)){
                    System.out.println("Successfully remove!");
                } else {
                    System.err.println("An error has occured during the removal! Please try again!");
                }
            }
        } while (validator.checkYesOrNo("Continue to remove crew member (Y/N)?"));
    }

    @Override
    public void saveAll() {
        if(flightDao.saveToFile()){
            System.out.println("Saving flights to database successfully!");
        } else{
            System.err.println("An error has occured during saving flights! Please try again!");
        }
        if(rervationDao.saveToFile()){
            System.out.println("Saving reservations to database successfully!");
        } else{
            System.err.println("An error has occured during saving reservations! Please try again!");
        }
        if(crewMemberDao.saveToFile()){
            System.out.println("Saving crew members to database successfully!");
        } else{
            System.err.println("An error has occured during saving crew members! Please try again!");
        }
        if(boardingPassDao.saveToFile()){
            System.out.println("Saving boarding passes to database successfully!");
        } else{
            System.err.println("An error has occured during saving boarding passes! Please try again!");
        }
    }

    @Override
    public void loadAll() {
        flightDao.loadFromFile();
        rervationDao.loadFromFile();
        crewMemberDao.loadFromFile();
        boardingPassDao.loadFromFile();
    }

    @Override
    public void showAllFromFile() {
        System.out.println("------Flight-------");
        System.out.println(String.format("%20s|%20s|%20s|%20s|%20s",
                "Flight Number", "Departure City", "Destination City", "Depature Time", "Arrival Time"));
        List<Flight> list = new ArrayList<>();
        list.addAll(flightDao.getAll());
        Collections.sort(list, Collections.reverseOrder(Flight.compareDate));
        for (Flight flight : list) {
            System.out.println(flight);
        }
        System.out.println("-------------------");
    }

}
