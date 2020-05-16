package minesweeper;

import javax.swing.JOptionPane;

public class minesweeper {

    public void start(minesweeper minesweeper) {
        Input = new input(minesweeper);
        Input.main(Input);
    }
    
    public void proceed(int size) {
        int toughness = 1;
        Object[] options = {"Kolay", "Orta", "Zor"};
        toughness = JOptionPane.showOptionDialog(null,
                "Mayın tarlasını ne şekilde oynamak istersiniz ?", "Seviye Seçimi",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if(toughness == -1)
            System.exit(0);
        newGame = new game(size, toughness);
        newGame.main(newGame, size);

    }
    
    public static void main(String[] args) {
        minesweeper = new minesweeper();
        minesweeper.start(minesweeper);
    }
    
    private static minesweeper minesweeper;
    private static game newGame;
    private static input Input;
}

