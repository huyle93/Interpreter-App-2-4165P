
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
/**
 * Class Operator extends TreeNode
 */

public class VariableNode extends TreeNode
{
  //-------------------- instance variables -----------------------
  String operator;
  private Float num;
  
  private SymbolTable st;
  //-------------------- constuctor -------------------------------
  public VariableNode( String _Variable, SymbolTable _Symbol )
  {
    st = _Symbol;
    cur = _Variable;
    
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