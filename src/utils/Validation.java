/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ASUS
 */
public class Validation implements IValidation{
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String inputString(String msg) {
        do {
            if(msg != null) {
                System.out.println(msg);
            }
            String data = scanner.nextLine();
            
            if(data.isEmpty()) {
                System.err.println("Value cannot be empty!");
                continue;
            }
            return data;
        } while(true);
    }

    @Override
    public String inputStringWithRegex(String errorMsg, String msg, String regex) {
        do {
            String data = inputString(msg);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            
            if(!matcher.matches()) {
                System.err.println(errorMsg);
                continue;
            }
            return data;
        } while(true);
    }

    @Override
    public int inputInteger(String msg, int min, int max) {
        do {
            String rawInput = inputString(msg);

            if (rawInput.isEmpty() || rawInput.contains(" ")) {
                return -1;
            }
            int returnNumber;
            try {
                returnNumber = Integer.parseInt(rawInput);
                if (returnNumber < min || returnNumber > max) {
                    System.err.println("Must input a number from " + min + " to " + max);
                    continue;
                }
            } catch (NumberFormatException ex) {
                System.err.println("Must enter a number");
                continue;
            }
        } while (true);
    }

    @Override
    public LocalDate inputDepatureTime(String msg) {
        do {
            String rawInput = inputString(msg);
            
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            try {
                LocalDate departureTime = LocalDate.parse(rawInput, dateTimeFormatter);
                return departureTime;
            } catch (DateTimeParseException exception) {
                System.err.println("Invalid time format. Please use dd/MM/yyyy HH:mm format");
                continue;
            }
        } while(true);
    }

    @Override
    public LocalDate inputArrivalTime(String msg, LocalDate depatureTime) {
        do {
            LocalDate arrivalTime = inputDepatureTime(msg);
            
            if(depatureTime.isAfter(arrivalTime)) {
                System.err.println("Arrival time cannot lower than Departure time!");
                continue;
            }
            return arrivalTime;
        } while(true);
    }

    @Override
    public LocalDate inputDate(String msg) {
        do {
            String rawInput = inputString(msg);
            
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            try {
                LocalDate date = LocalDate.parse(rawInput, dateTimeFormatter);
                return date;
            } catch (DateTimeParseException exception) {
                System.err.println("Invalid date format. Please use dd/MM/yyyy format");
                continue;
            }
        } while (true);
    }

    @Override
    public String inputReservation(String msg, String regex) {
         do {
            String data = inputString(msg);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            
            if(!matcher.find()) {
                System.out.println("Please input correct format (Rxyzt with xyzt is a number)");
                continue;
            }
            return data;
        } while(true);
    }

//    @Override
//    public String inputSeat(String msg) {
//       String rawInput = inputString(msg);
//       String regex = "^[a-zA-Z][1-6]$";
//       Pattern pattern = Pattern.compile(regex);
//       Matcher matcher = pattern.matcher(rawInput);
//       
//       if(!matcher.matches()) {
//           return false;
//       }
//    }
// "Please input correct format (Fxyzt with xyzt is a number)"

}
