/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public interface IValidation {
    String inputString(String msg);
    String inputStringWithRegex(String errorMsg, String msg, String regex);
    int inputInteger(String msg, int min, int max);
    LocalDateTime inputDepatureTime(String msg);
    LocalDateTime inputArrivalTime(String msg, LocalDateTime depatureTime);
    LocalDate inputDate(String msg);
    String inputReservation(String msg, String regex);
    boolean checkYesOrNo(String msg);
}
