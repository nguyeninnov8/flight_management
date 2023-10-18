/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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
                return returnNumber;
            } catch (NumberFormatException ex) {
                System.err.println("Must enter a number");
            }
        } while (true);
    }

    @Override
    public LocalDateTime inputDepatureTime(String msg) {
        do {
            String rawInput = inputString(msg);
            
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            try {
                LocalDateTime departureTime = LocalDateTime.parse(rawInput, dateTimeFormatter.withResolverStyle(ResolverStyle.SMART));
                return departureTime;
            } catch (DateTimeParseException exception) {
                System.err.println("Invalid time format. Please use dd/MM/yyyy HH:mm format");
            }
        } while(true);
    }

    @Override
    public LocalDateTime inputArrivalTime(String msg, LocalDateTime depatureTime) {
        do {
            LocalDateTime arrivalTime = inputDepatureTime(msg);
            
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
                LocalDate date = LocalDate.parse(rawInput, dateTimeFormatter.withResolverStyle(ResolverStyle.SMART));
                return date;
            } catch (DateTimeParseException exception) {
                System.err.println("Invalid date format. Please use dd/MM/yyyy format");
            }
        } while (true);
    }
    
    @Override
    public boolean checkYesOrNo(String msg) {
        while (true) {
            String input = inputString(msg);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must input Y or N to select option");
            }
        }
    }

}
