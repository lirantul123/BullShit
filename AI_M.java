import java.util.*;

// Goal: find the number after ans
/** If there more than one specific word and you wnat to get to the second for instance,
 * I will ask you for which one - if you want also I will execute a method to lock for them
**/


class HelloWorld {
    public static Scanner in = new Scanner(System.in);
    public static int wordPlace = 0;

    public static void main(String[] args) {
        String finalResponse = "";
        boolean dontContinue = false;
        String text, ques;
        
        try {
            System.out.print("Text: ");
            text = in.nextLine();
            System.out.print("Request: ");
            ques = in.nextLine();
            System.out.println();

            String ans = findWord(text, ques);
            finalResponse += ans + " in";

            int numberOfThisWord = howManyTimesAppear(text, ans);
            if (numberOfThisWord != 1) {
                String[] createWordsWithWord = createSentencesWithWord(text, ans, numberOfThisWord);                
                
                String holderOfSentences = "[ ";
                for (int i = 0; i < createWordsWithWord.length; i++) {
                    if (i == createWordsWithWord.length - 1)
                        holderOfSentences += createWordsWithWord[i] + " ]";
                    else 
                        holderOfSentences += createWordsWithWord[i] + " | ";
                }

                String strOfNumbers = countNumbers(numberOfThisWord);
                System.out.println(holderOfSentences + "\n " + "Which: " + strOfNumbers + "- ");
                int whichWord = in.nextInt();

                while (whichWord > numberOfThisWord || whichWord < 1) {
                    System.out.println(holderOfSentences + "\n " + "Which: " + strOfNumbers + "- ");
                    whichWord = in.nextInt();
                }

                System.out.println();
                String[] wordsWithWord = createWordsWithWord[whichWord - 1].split("[\\s\\n]+");
                System.out.println(wordsWithWord[1] + " in: " + wordsWithWord[2]);

                dontContinue = true;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return;
        }

        System.out.println();

        if (!dontContinue) {
            String resultedNumber = findResultedNumber(text);
            finalResponse += ": " + resultedNumber;

            System.out.println(finalResponse);
        }
    }

    public static String countNumbers(int numberOfThisWord) {
        boolean finishedShowNumbers = false;
        String strOfNumbers = "{ ";
        for (int i = 0; i < numberOfThisWord; i++) {
            if (finishedShowNumbers)
                break;
            if (i != numberOfThisWord - 3)
                strOfNumbers += (i + 1) + ", ";
            if (i == numberOfThisWord - 2){
                strOfNumbers += (i + 2) + " }";
                finishedShowNumbers = true;
            }
        }
        return strOfNumbers;
    }

    public static String[] createSentencesWithWord(String text, String ans, int numberOfThisWord) {
        String[] createWordsWithWord = new String[numberOfThisWord];
        int j = 0;
        String[] textWords = text.split("[\\s\\n]+");
        for (int i = 0; i < textWords.length; i++) {
            if (textWords[i].equals(ans)) {
                createWordsWithWord[j] = textWords[i - 1] + " '" + textWords[i] + "' " + textWords[i + 1];
                j++;
            }
        }
        // String[] sendAfterFixed = new String[numberOfThisWord];
        // int k = 0;
        // for (int i = 0; i < sendAfterFixed.length; i++){
        //     if (i != numberOfThisWord-2){
        //         sendAfterFixed[k] = createWordsWithWord[k];
        //         k++;
        //     }
        // }
            
        return createWordsWithWord;
    }

    public static int howManyTimesAppear(String text, String ans) {
        int count = 0;
        String[] textWords = text.split("[\\s\\n]+");
        for (int i = 0; i < textWords.length; ++i) {
            if (textWords[i].equals(ans))
                count++;
        }
        return count;
    }

    public static String findResultedNumber(String text) {
        String number = "";
        boolean b = true;
        String[] textWords = text.split("[\\s\\n]+");

        for (int i = wordPlace; i < textWords.length; i++) {
            b = true;
            for (int j = 0; j < textWords[i].length(); j++) {
                for (char k = 97; k < 123; k++) {
                    if (b) {
                        if ((char) k == textWords[i].charAt(j))
                            b = false;
                    }
                }
            }

            if (b) {
                number = textWords[i];
                break;
            }
        }
        return number;
    }

    public static String findWord(String text, String ques) {
        String ans = "";

        String[] textWords = text.split("[\\s\\n]+");
        String[] quesWords = ques.split("\\s+");

        boolean[] used = new boolean[textWords.length];
        boolean matchFound = false;

        for (int i = 0; i < quesWords.length; i++) {
            String quesWord = quesWords[i].toLowerCase();
            for (int j = 0; j < textWords.length; j++) {
                if (!used[j] && quesWord.equals(textWords[j].toLowerCase())) {
                    ans = textWords[j];
                    wordPlace = j;
                    used[j] = true;
                    matchFound = true;
                    break;
                }
            }
        }

        if (!matchFound) {
            System.out.println("No matching words found.");
        }

        return ans;
    }
}
