package Communication;

public class TicTacToe {
    public String[][] place;

    public TicTacToe() {
        place = new String[3][3];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                place[i][j] = " ";
            }
        }
    }

    public String print() {
        String board = " _ _ _\n";

        for (int i = 0; i < 3; i++) {
            board += "|";
            for (int j = 0; j < 3; j++) {
                board += place[i][j] + "|";
            }
            board += "\n";
        }
        board += " _ _ _\n";

        return board;
    }

    public boolean setPlace(int row, int col, String symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && place[row][col].equals(" ")) {
            place[row][col] = symbol;
            return true;
        }
        return false;
    }
}