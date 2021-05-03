import java.util.Random;
public class Word {
  private String contents;
  private boolean isSelected;
  private int charIndex;
  private int xLocation;
  private int yLocation;
  
  public Word(String str) {
    this.contents = str;
    this.charIndex = 0;
    this.isSelected = false;
    this.yLocation =  new Random().nextInt(Main.HEIGHT);
    this.xLocation = Main.WIDTH - 25;
  }
  
  public boolean getIsSelected() { return isSelected; }
  public String getContents() { return contents; }
  public char getCurrentChar() { return contents.charAt(charIndex); }
  public int getX() { return xLocation; };
  public int getY() { return yLocation; };
  
  public void charTyped() { charIndex++; };
  public void select() { isSelected = true; }
  public void move () { xLocation -= (Main.WIDTH / 15); }
}