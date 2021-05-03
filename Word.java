import java.util.Random;
public class Word {
  private String contents;
  private int charIndex;
  private int xLocation;
  private int yLocation;
  
  public Word(String str) {
    this.contents = str;
    this.charIndex = 0;
    this.yLocation =  new Random().nextInt(Main.HEIGHT - 40) + 40;
    this.xLocation = Main.WIDTH - 25;
  }
  
  public String getContents() { return contents; }
  public char getCurrentChar() { return contents.charAt(charIndex); }
  public int getCurrentIndex() { return charIndex; }
  public int getX() { return xLocation; };
  public int getY() { return yLocation; };
  
  public void charTyped() { charIndex++; };
  public void move () { xLocation -= (Main.WIDTH / 150); }
}

// goals:
/*
 add scoring
 track which letters/words are being typed
 
*/