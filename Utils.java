import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
  // Adds all words from the text file w/ the specified number of letters
  // to a List
  /**
   * Reads all words from the .txt file and adds the ones less than or equal
   * to the desired length to a list
   * @param numLetters The maximum number of letters desired per word
   * @return A list containing all words from the text file of a certain length
   */
  public static List<String> setUpWords(int numLetters) {
    try {
      Scanner scanner = new Scanner(new File("engmix.txt"));
      List<String> list = new ArrayList<String>();

      while (scanner.hasNext()) {
        list.add(scanner.nextLine());
      }

      scanner.close();
      return list;
    }
    catch(FileNotFoundException fnfe) {
      //TODO handle exception here???
      return null;
    }
  }

  /**
   * Determines which letters are acceptable input based on the words on the
   * screen and the user's current progress typing them
   * @param wordsOnScreen A list containing all words currently onscreen
   * @return a list of characters containing the first character of 
   * every word onscreen or list containining a single letter if a word has
   * already been selected
   */
  public static List<Character> getAcceptableLetters(List<Word> wordsOnScreen){
    List<Character> acceptableLetters = new ArrayList<Character>();
    
    // check to see if a word has already been selected
    Word selectedWord = null;
    for (Word word : wordsOnScreen) { 
      if (word.getIsSelected()) {
        selectedWord = word;
        break;
      }
    }
    
    if (selectedWord == null)  // no words has been selected yet
      for (Word word : wordsOnScreen) { acceptableLetters.add(word.getContents().charAt(0)); }
    else {  // a word has already been selected
      char currentLetter = selectedWord.getCurrentChar();
      acceptableLetters.add(currentLetter);
    }
    
    return acceptableLetters;
  }
}
