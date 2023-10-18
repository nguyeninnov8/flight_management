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
import data_objects.ICrewMemberDao;
import data_objects.IDaoFactory;
import data_objects.IFlightDao;
import data_objects.IReservationDao;
import java.time.LocalDate;
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
    static final ICrewMemberDao crewMemberDao = factoryDao.crewMemberDao();

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
        LocalDate departureTime = validator.inputDepatureTime("Input departure time in HH:mm:ss format (e.g., 12:30): ");
        LocalDate arrivalTime = validator.inputArrivalTime("Input arrival time in HH:mm:ss format (e.g., 12:30): ", departureTime);
        Flight toAddFlight = new Flight(flightNumber, depatureCity, destinationCity, departureTime, arrivalTime);
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

        if (resultList == null) {
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
            reservationId = validator.inputReservation("Please input reseravation id: ", "R\\d{4}");
            if (rervationDao.checkReservationExist(reservationId)) {
                System.err.println("This reservation id does not exist!\n"
                        + "Please check again!");
                continue;
            }
            break;
        }

        flightDao.showAllSeats(rervationDao.getReservation(reservationId).getReservedFlight());
        while (true) {
            choosedSeat = validator.inputStringWithRegex("Please input correct format (e.g., A1, A2, A3)", "Please choose your seat (e.g., A1, A2, A3,...): ", "^[a-zA-Z][1-6]$");
            if (!flightDao.setValidSeat(rervationDao.getReservation(reservationId).getReservedFlight(), choosedSeat)) {
                System.err.println("Please input valid seat!");
                continue;
            }
            break;
        }
        BoardingPass boardingPass = new BoardingPass(rervationDao.getReservation(reservationId).getReservedPassenger(), choosedSeat, rervationDao.getReservation(reservationId).getReservedFlight());
        viewBoardingPass(boardingPass);
    }

    private void viewBoardingPass(BoardingPass boardingPass) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("-------------------------------------");
        System.out.println("|            Boarding Pass          |");
        System.out.println("-------------------------------------");
        System.out.format("|NAME: %s                            |\n", (boardingPass.getPassenger().getFirstName() + " " + boardingPass.getPassenger().getLastName()));
        System.out.format("|FROM: %s                            |\n", boardingPass.getFlight().getDepartureCity());
        System.out.format("|TO: %s                              |\n", boardingPass.getFlight().getDestinationCity());
        System.out.format("|TIME:                               |\n");
        System.out.format("|      DEPARTURE TIME: %s           |\n", boardingPass.getFlight().getDepartureTime().format(dateTimeFormatter));
        System.out.format("|      ARRIVAL TIME: %s              |\n", boardingPass.getFlight().getArrivalTime().format(dateTimeFormatter));
        System.out.format("|FLIGHT: %s                           |\n", boardingPass.getFlight().getFlightNumber());
        System.out.format("|SEAT: %s                            |\n", boardingPass.getSeat());
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
        String phoneNumber = validator.inputStringWithRegex("Please input correct format (10 digits)", "Input phone number: ", "^\\d{10}$");
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
        System.out.println("/t------Crew Members-------/t");
        System.out.println(String.format("%12s|%12s|%12s|%12s|%12s|%25s|%12s",
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

}
