import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
/**
 * Class Operator extends TreeNode
 */

public class NumberNode extends TreeNode
{
  //-------------------- instance variables -----------------------
  Float num;
  //-------------------- constuctor -------------------------------
  public NumberNode( float number )
  {
    num = number;
    cur = "" + num;
  }
  public float eval()
  {
    return num;
  }
  public String data()
  {
    return cur;
  }
}