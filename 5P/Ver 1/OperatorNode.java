import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;

/**
 * OperatorNode
 * Huy Le
 */

public class OperatorNode extends TreeNode
{
  //-------------------- instance variables -----------------------
  public TreeNode left;
  public TreeNode right;

  static final String [] operator = { "+", "-", 
      "*", "%", "/", "(", ")", "=" };
  //-------------------- constuctor -------------------------------
  public OperatorNode( String c, TreeNode leftnode, TreeNode rightnode)
  {
    cur = c;
    left = leftnode;
    right = rightnode;
  }
  
  public float eval(  )
  {
    float leftt = left.eval();
    float rightt = right.eval();
    
    if( cur == ("+") )
    {
      return leftt + rightt;
    }
    
    else if( cur == ( "-" ) )
    {
        return leftt - rightt;
    }
    
    else if( cur == ( "/" ) )
    {
        return leftt / rightt;
    } 
    
    else if( cur == ( "%" ) ) 
    {
        return leftt % rightt;
    }
    else if( cur == ( "*" ) )
    {
        return leftt * rightt;
    }
    else if( cur == ( "=" ) )
    {
        stable.instance().setValue( left.cur, rightt );
        return leftt = rightt;
    }
    return 0.0f;    
  }
  public String data()
  {
    return cur;
  }
}