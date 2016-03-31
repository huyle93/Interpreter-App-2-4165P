//-------------------------- TreeNode.java ------------------------------
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
/**
 * TreeNode - represents a d.s. node graphicially as a JLabel
 * @author rdb
 */
//FROM THE LAB
public abstract class TreeNode
{ 
  protected String cur;
  public abstract float eval( );
  public abstract String data();
  public SymbolTable stable;
}