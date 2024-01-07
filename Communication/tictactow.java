public class tictactow {

    public String print(){
        String board = " _ _ _\n";

        for (int i = 0; i < 3; i++) {
            board += "|";
            for (int j = 0; j < 3; j++) {
                board += "_|";
            }
            board += "\n";
        }
        return board;
    }
}
