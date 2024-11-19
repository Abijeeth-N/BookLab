import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.URL;

class App {
  public static void main(String[] args) throws FileNotFoundException {
  
  String alice = "https://www.gutenberg.org/cache/epub/11/pg11.txt"; //Alice In Wonderland, sos book
  String monkey = "https://www.gutenberg.org/cache/epub/12122/pg12122.txt"; //The Monkey's Paw, my book
  

  int wordCount = 0;

  Book the_monkeys_paw = new Book(monkey, true);
  Book alice_in_wonder_land = new Book(alice, true);

  the_monkeys_paw.toTXT("pigLatin_the_monkeys_paw");
  System.out.println("The Monkey's Paw contains "+ the_monkeys_paw.countWords()+ " words.");
  wordCount += the_monkeys_paw.countWords();

  alice_in_wonder_land.toTXT("pigLatin_alice_in_wonderland");
  System.out.println("Alice In Wonderland contains " + alice_in_wonder_land.countWords() + " words.");
  wordCount += alice_in_wonder_land.countWords();

  System.out.println("Total word count of both books combined: "+wordCount);


  }
}
