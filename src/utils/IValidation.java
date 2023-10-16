/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ASUS
 */
public interface IValidation {
    String inputString(String msg);
    String inputStringWithRegex(String errorMsg, String msg, String regex);
    int inputInteger(String msg, int min, int max);
    LocalDate inputDepatureTime(String msg);
    LocalDate inputArrivalTime(String msg, LocalDate depatureTime);
    LocalDate inputDate(String msg);
    String inputReservation(String msg, String regex);
}
