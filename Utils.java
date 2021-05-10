import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Utils {
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
      String str;  // stores each word read by the scanner

      // read the file
      while (scanner.hasNext()) {
        str = scanner.nextLine();
        if (str.length() <= numLetters) list.add(str);
      }
      
      scanner.close();
      return list;
    }
    catch(FileNotFoundException fnfe) { return null; }//TODO handle exception here???
  }

  /**
   * Determines which letters are acceptable input based on the words on the
   * screen and the user's current progress typing them
   * @param wordsOnScreen A list containing all words currently onscreen
   * @return a list of characters containing the first character of 
   * every word onscreen or list containining a single letter if a word has
   * already been selected
   */
  public static List<Character> getAcceptableLetters(List<Word> wordsOnScreen, Word selectedWord){
    List<Character> acceptableLetters = new ArrayList<Character>();

    if (selectedWord == null)  // no word has been selected yet
      for (Word word : wordsOnScreen) { acceptableLetters.add(word.getCurrentChar()); }
    else { acceptableLetters.add(selectedWord.getCurrentChar()); } // a word has already been selected 

    return acceptableLetters;
  }

  /**
   * Adds another word to the screen from the list of all words
   * @param allWordsList A list of all words from the .txt file w/ the correct length
   * @param wordsOnScreen A list of the words already on the screen
   */
  public static void addWordToScreen(List<String> allWordsList, List<Word> wordsOnScreen) {
    int newIndex = new Random().nextInt(allWordsList.size());
    Word newWord = new Word(allWordsList.remove(newIndex));
    wordsOnScreen.add(newWord);
  }
}

//
////TODO:
//set up rules page
// implement health/high score
// handle running out of words
// problems with css directory structure
// add extra images behind words
// user selection mode for word length
  
