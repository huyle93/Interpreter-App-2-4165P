
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
/**
 * Class Operator extends TreeNode
 * Huy Le
 */

public class VariableNode extends TreeNode
{
  //-------------------- instance variables -----------------------
  String operator;
  private Float num;
  
  private SymbolTable st;
  //-------------------- constuctor -------------------------------
  public VariableNode( String var, SymbolTable sym )
  {
    st = sym;
    cur = var;
  }
  
  public float eval()
  {
    num = st.getValue( cur );
    return num;
  }
    public String data()
  {
    return cur;
  }
}