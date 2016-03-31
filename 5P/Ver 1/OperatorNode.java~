/**
 * Author: Dhairya Nishar
 */

import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;

public class OperatorNode extends TreeNode
{
  //-------------------- instance variables -----------------------
  public TreeNode left;
  public TreeNode right;

  static final String [] operator = { "+", "-", "*", "%", "/", "(", ")", "=" };
  //-------------------- constuctor -------------------------------
  public OperatorNode( String _Cur, TreeNode _Lnode, TreeNode _Rnode)
  {
    cur = _Cur;
    left = _Lnode;
    right = _Rnode;
  }
  
  public float eval(  )
  {
    float leftt = left.eval();
    float rightt = right.eval();
    
    if( cur.equals ("+") )
    {
      return leftt + rightt;
    }
    
    else if ( cur.equals( "-" ) )
    {
        return leftt - rightt;
    }
    
    else if ( cur.equals( "/" ) )
    {
        return leftt / rightt;
    } 
    else if ( cur.equals( "%" ) )  
    {return leftt % rightt;}
    else if ( cur.equals( "*" ) )
    {return leftt * rightt;}
    else if ( cur.equals( "=" ) )
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