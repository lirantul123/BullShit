public class tictactow {
    public String[][] place;

    public tictactow() {
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

    public void setPlace(int i, int j, String sym) {
        place[i][j] = sym;
    }
}
