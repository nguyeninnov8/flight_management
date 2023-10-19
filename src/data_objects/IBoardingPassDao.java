/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.BoardingPass;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface IBoardingPassDao {
    List<BoardingPass> getAll();
    BoardingPass getBoardingPass(BoardingPass boardingPass);
    boolean addBoardingPass(BoardingPass boardingPass);
    boolean updateBoardingPass(BoardingPass boardingPass);
    boolean deleteBoardingPass(BoardingPass boardingPass);
}
