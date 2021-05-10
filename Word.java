import java.util.Random;
public class Word {
  private String contents;
  private int charIndex;
  private int xLocation;
  private int yLocation;
  private static int lastYLocation = 0;
  
  public Word(String str) {
    this.contents = str;
    this.charIndex = 0;
    this.xLocation = Main.WIDTH - 25;
    // pick a y location that is far away from the last word created
    int difference;
    do {
      this.yLocation =  new Random().nextInt(Main.HEIGHT - 40) + 40;
      difference = Math.abs(this.yLocation - lastYLocation);
    }
    while (difference < 40);
    lastYLocation = this.yLocation;
  }
  
  public String getContents() { return contents; }
  public char getCurrentChar() { return contents.charAt(charIndex); }
  public int getCurrentIndex() { return charIndex; }
  public int getX() { return xLocation; };
  public int getY() { return yLocation; };
  
  public void charTyped() { charIndex++; };
  public void move () { xLocation -= (Main.WIDTH / 150); }
}