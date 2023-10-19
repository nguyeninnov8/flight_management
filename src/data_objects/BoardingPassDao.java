/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_objects;

import business_object.BoardingPass;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class BoardingPassDao implements IBoardingPassDao{
    List<BoardingPass> boardingPasses;

    public BoardingPassDao() {
        this.boardingPasses = new ArrayList<>();
    }

    @Override
    public List<BoardingPass> getAll() {
        return boardingPasses;
    }

    @Override
    public BoardingPass getBoardingPass(BoardingPass boardingPass) {
        return boardingPasses.get(boardingPasses.indexOf(boardingPass));
    }

    @Override
    public boolean addBoardingPass(BoardingPass boardingPass) {
        return boardingPasses.add(boardingPass);
    }

    @Override
    public boolean updateBoardingPass(BoardingPass boardingPass) {
        BoardingPass updatedBoardingPass = getBoardingPass(boardingPass);
        if(updatedBoardingPass != null) {
            updatedBoardingPass = boardingPass;
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBoardingPass(BoardingPass boardingPass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
