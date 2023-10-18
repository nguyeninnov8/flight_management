package action_service;

import business_object.RoleMember;

public class Menu {

    static final Service service = new Service();

    public static void mainMenu() {
        service.loadAll();
        int choice;
        do {
            System.out.println("---MAIN MENU---\n"
                    + "1. Flight schedule management\n"
                    + "2. Passenger reservation and booking\n"
                    + "3. Passenger check-in and seat allocation\n"
                    + "4. Crew management\n"
                    + "5. Administrator access for system management\n"
                    + "6. Data storage for flight details, reservations, and assignments\n"
                    + "7. Print all lists from file\n"
                    + "8. Close the application");
            choice = Service.validator.inputInteger("Input the choice:", 1, 8);
            switch (choice) {
                case 1:
                    service.addNewFlight();
                    break;
                case 2:
                    service.passengerReservation();
                    break;
                case 3:
                    service.checkIn();
                    break;
                case 4:
                    subMenu_crewManagement();
                    break;
                case 5:
                    // incoming
                    break;
                case 6:
                    service.saveAll();
                    break;
                case 7:

            }
        } while (choice != 8);
    }
    
    public static void subMenu_crewManagement() {
        int choice;
        do {
            System.out.println("---CREW MANAGEMENT---\n"
                    + "1. Add new crew member\n"
                    + "2. Show all crew members\n"
                    + "3. Delete crew member\n"
                    + "4. Back to main menu");
            choice = Service.validator.inputInteger("Input the choice:", 1, 4);
            switch (choice) {
                case 1:
                    subMenu_addCrew();
                    break;
                case 2:
                    service.showAllCrewMembers();
                    break;
                case 3:
                    service.deleteCrewMember();
            }
        } while (choice != 4);
    }
    
    public static void subMenu_addCrew(){
        int choice;
        do {
            System.out.println("---CHOOSE ROLE MEMBER---\n"
                    + "1. PILOT\n"
                    + "2. FLIGHT ATTENDANT\n"
                    + "3. GROUND STAFF"
                    + "4. Back to main menu");
            choice = Service.validator.inputInteger("Input the choice:", 1, 4);
            switch (choice) {
                case 1:
                    service.addCrewMember(RoleMember.PILOT);
                    break;
                case 2:
                    service.addCrewMember(RoleMember.FLIGHT_ATTENDANT);
                    break;
                case 3:
                    service.addCrewMember(RoleMember.GROUND_STAFF);
            }
        } while (choice != 4);
    }
}
