import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI_M_eazy_ {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        String sentence = "";
        String wordToFind = "";
        
        System.out.print("Sentence: ");
        sentence = in.nextLine();
        System.out.print("word: ");
        wordToFind = in.nextLine();


        String regex = wordToFind + "\\D*(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sentence);

        while (matcher.find()) {
            int groupCount = matcher.groupCount();

            if (groupCount > 0) {
                System.out.println("Word: " + wordToFind);

                for (int i = 1; i <= groupCount; i++) {
                    System.out.println("Match " + i + ": " + matcher.group(i));
                    
                }
            }
        }

        System.out.print("Enter the match number to retrieve: ");
        int chosenMatch = in.nextInt();

        matcher.reset();
        for (int matchNumber = 1; matchNumber <= chosenMatch; matchNumber++) {
            if (matcher.find()) {
                if (matchNumber == chosenMatch) {
                    System.out.println("Chosen Match: " + matcher.group(1));
                    break;
                }
            } else {
                System.out.println("Invalid match number.");
                break;
            }
        }
    }
}
