/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ist411usingjsonwithrest;

import java.util.ArrayList;

/**
 *
 * @author Dylan
 */
public class BoardsList {

    private static BoardsList instance;
    private static ArrayList<Board> boardsList;

    private BoardsList() {
        BoardsList.boardsList = new ArrayList<>();
    }

    public static BoardsList getInstance() {
        if (instance == null) {
            instance = new BoardsList();
            instance.getBoardsList().add(new Board("BoardTest", "This is a test board."));
        }
        return instance;
    }

    /**
     * @return the boardsList
     */
    public ArrayList<Board> getBoardsList() {
        return boardsList;
    }

    /**
     * @param boardsList the boardsList to set
     */
    public void setBoardsList(ArrayList<Board> boardsList) {
        this.boardsList = boardsList;
    }
}
