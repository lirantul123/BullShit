// import org.jsoup.Jsoup;
// import org.jsoup.nodes.Document;
// import org.jsoup.nodes.Element;
// import org.jsoup.select.Elements;

// import java.io.IOException;
// import java.util.Scanner;

// public class QuestionAnsweringSystem {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//             System.out.print("Ask a question (or type 'exit' to quit): ");
//             String question = scanner.nextLine();

//             if (question.equalsIgnoreCase("exit")) {
//                 System.out.println("Goodbye!");
//                 break;
//             }

//             try {
//                 String answer = getAnswer(question);
//                 System.out.println("Answer: " + answer);
//             } catch (IOException e) {
//                 System.err.println("Error: " + e.getMessage());
//             }
//         }
//     }

//     public static String getAnswer(String question) throws IOException {
//         String searchQuery = "site:wikipedia.org " + question; // You can customize the search engine query

//         // Perform a Google search
//         String googleUrl = "https://www.google.com/search?q=" + searchQuery;
//         Document googleSearch = Jsoup.connect(googleUrl).get();

//         // Extract the first search result
//         Elements searchResults = googleSearch.select(".g");
//         if (searchResults.size() > 0) {
//             Element firstResult = searchResults.first();
//             Element link = firstResult.select("a").first();
//             String title = link.text();
//             String url = link.attr("href");

//             // Fetch information from the first search result (you may need to adapt this for different types of questions)
//             if (url.startsWith("http") && url.contains("wikipedia.org")) {
//                 Document wikipediaPage = Jsoup.connect(url).get();
//                 Element bodyContent = wikipediaPage.select(".mw-parser-output").first();
//                 return bodyContent.text();
//             }

//             // You can add more logic here for different types of websites and answers

//             return "I'm sorry, I couldn't find an answer to your question.";
//         } else {
//             return "I'm sorry, I couldn't find an answer to your question.";
//         }
//     }
// }
