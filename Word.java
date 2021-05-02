
public class Word {
  private String contents;
  private boolean isSelected;
  private int charIndex;
  
  public Word(String str) {
    this.contents = str;
    this.charIndex = 0;
    this.isSelected = false;
  }
  
  public boolean getIsSelected() { return isSelected; }
  public String getContents() { return contents; }
  public char getCurrentChar() { return contents.charAt(charIndex); }
  
  public void charTyped() { charIndex++; };
  public void select() { isSelected = true; }
}