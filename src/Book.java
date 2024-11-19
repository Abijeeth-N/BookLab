import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.net.URL;


//A few assumptions.......

//Words will be separated by spaces. 
//There can be punctuation in a word, we will only add/keep punctuation at the end of a string if it is at the end of a string.
//    for examples: Hello. ==> Ellohay.    Good-bye! ==> Ood-byegay!    so... ==> osay...

public class Book
{

  private final String vowels = "AEIOUaeiou";
  private String bookText = "";
  private String translatedText= "";

 //public Book(URL url) {
  //  bookText = this.readBook(url); 
 // }

  public Book(String book, boolean readFromLink) 
  {
    if (readFromLink) bookText = readBook(book);
    else bookText = book;
  }


  public String readBook(String link) 
  {
    String result = "";
    boolean reading = false;
    try 
    {
      URL url = new URL(link);
      Scanner sc = new Scanner(url.openStream());

      while (sc.hasNext()) 
      {
        String text = sc.nextLine();
        if (text.indexOf("*** END") > -1) reading = false;

        if(reading) result += text + " \n";

        if (text.indexOf("*** START") > -1) reading = true;
        
       
      }

    }

    
    
    catch(IOException ex) 
    {
      ex.printStackTrace();
    }

    return result;
  }

  




  public String pigLatin(String word)
  {
    boolean capital = false;
    boolean startsWithVowel = false;
    String first = "", last = "", result = "";
    String punctuationFront = "", punctuationBack = "";
    int index1 = 0, index2 = word.length();

    //this method accounts for extra punctuation, such as quotes, exclamations, apostrophes, etc
    //hence the check for front/back punctuation

    //front punctuation
    for (int i = 0; i < word.length(); i++) 
    {
      char letter = word.charAt(i);
      if ( (letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122) || (letter == 45) ) 
      {
        index1 = i;
        punctuationFront = word.substring(0,i);

        //break out
        i = word.length();
      }
    }

    //back punctuation
    for (int i = index1; i < word.length(); i++) 
    {
      char letter = word.charAt(i);
      if (! ((letter >= 65 && letter <= 90) || (letter >= 97 && letter <= 122) || (letter == 45)) ) 
      {
        index2 = i;
        
        punctuationBack = word.substring(index2);

        //break out
        i = word.length();
      }
    }

    //find the first and last part 
    for (int i = index1; i < index2; i++) 
    {
      //compares the current letter to each vowel, listed in the vowel string
      if ( vowels.indexOf(word.substring(i,i+1)) > -1 )
      {
        first = word.substring(index1,i);
        last = word.substring(i,index2);
        //break out
        i = word.length();
        break;
      }
   }

   //account for if the word uses 'y' as a vowel
   if (first.length() == 0 && last.length() == 0 && word.indexOf("y") > -1 && (index1 < word.indexOf("y") 
   && word.indexOf("y") < index2)) {
    first = word.substring(index1,word.indexOf("y"));
    last = word.substring(word.indexOf("y"), index2);
   }

   //account for if the String is all punctuation
   if (last.length() == 0 && first.length() == 0) return word;

   //check if the word starts with a vowel
   if (first.length() == 1 || (last.length() == 1 && vowels.indexOf(last) > -1)) startsWithVowel = true;
    

    //special case for words in all caps, ignore if not
    if (word.substring(index1,index2).equals(word.substring(index1,index2).toUpperCase()))  //checks for all caps
    {
      if (startsWithVowel) 
      {
        if (last.equals("I") && first.length() == 0) {
          result = last + first + "yay"; //if the character is just first-person "I"
        }
        else result += last + first + "YAY"; 
      }
      else result =  last + first + "AY";
      result = punctuationFront + result + punctuationBack; //append any other punctuation
      return result;
    } 

    //check for capital letter
    if ( word.substring(index1,index1+1).equals(word.substring(index1,index1+1).toUpperCase()) ) capital = true;

    //capitalize as needed
    if (capital) 
    {
      result = last.substring(0,1).toUpperCase() + last.substring(1) + first.toLowerCase() + "ay";
    }
    else result = last + first + "ay";
    

    //if there is extra punctuation, append that here
    result = punctuationFront + result + punctuationBack;

    
    return result;
  }
  






  public void translateBook()
  {
    String text = this.getText();
    String result = "";
    String word = "";
    int index1 = 0, index2 = 0;
    
    
    while (text.indexOf(" ", index1) > -1) 
    {
      index2 = text.indexOf(" ", index1);
      word =  text.substring(index1, index2);
      
      word = pigLatin(word);

      result += word + " ";
      index1 = index2+1;
    }
     result += pigLatin(text.substring(index1));

    this.translatedText=result;
    
  }




  public int countWords()
    {
      String text = this.translatedText;
      if (text.length() == 0) text = bookText;
      String word = "";
      int index1 = 0, index2 = 0;
      int wordCount = 0;
      
      while (text.indexOf(" ", index1) > -1) 
      {
        index2 = text.indexOf(" ", index1);
        word =  text.substring(index1, index2);
        if (word.length() > 0 && !word.equals(" ") && !word.equals("\n")) wordCount++;
        index1 = index2+1;
      }
      return wordCount;
    }
     
  



  public String getText() {
    return bookText;
  }



  public void toTXT(String filename)  throws FileNotFoundException {
    String book;
    if (translatedText.length() >= 1) book = translatedText;
    else book = bookText;
    
    PrintWriter out = new PrintWriter(filename+".txt");
    out.println(book);
    out.close();
  }




}  
