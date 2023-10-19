
package data_objects;

import business_object.BoardingPass;
import java.util.ArrayList;
import java.util.List;
import utils.HandlingFile;

public class BoardingPassDao implements IBoardingPassDao{
    private final String BOARDINGPASS_FILEPATH = "src\\boardingPass.dat";
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
        return boardingPasses.remove(boardingPass);
    }

    @Override
    public boolean isExistReservationId(String reservationId) {
        for (BoardingPass boardingPasse : boardingPasses) {
            if(boardingPasse.getReservation().getId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean saveToFile() {
        return new HandlingFile<BoardingPass>().saveToFile(BOARDINGPASS_FILEPATH, boardingPasses);
    }

    @Override
    public boolean loadFromFile() {
        return new HandlingFile<BoardingPass>().loadFromFile(BOARDINGPASS_FILEPATH, boardingPasses);
    }
    
}
