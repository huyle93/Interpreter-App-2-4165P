/**
 * Interpreter.java - parses string input representing an infix arithmetic
 *                 expression into tokens, and builds an expression tree.
 *                 The expression can use the operators =, +, -, *, /, %.
 *                 and can contain arbitrarily nested parentheses.
 *                 The = operator is assignment and must be absolutely lowest
 *                 precedence.
 * March 2013
 * rdb
 */
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Interpreter 
{
    //----------------------  class variables  ------------------------
    
    //---------------------- instance variables ----------------------
    private boolean      _printTree = true;  // if true print tree after each
    //    expression tree is built 
    float value;
    ArrayList<String> theOperator;
    Scanner scanner1;
    private Stack<String> theOperatorStack;
    //----------- constants
    
    
    private Stack<TreeNode> theStack;
    public OperatorNode theOperatorNode;
    public TreeNode treeRoot;
    public SymbolTable symbolTable;
    
    //--------------------------- constructor -----------------------
    /**
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     */
    public Interpreter( String[] args ) 
    {  
        
        symbolTable = SymbolTable.instance();
        theOperatorStack = new Stack<String>();
        theStack = new Stack<TreeNode>();
        theOperator = new ArrayList<String>();
        
        if ( args.length > 0 )
            processFile( args[ 0 ] );
        else
            interactive();      
    }
    //--------------------- processFile -----------------------------
    /**
     * Given a String containing a file name, open the file and read it.
     * Each line should represent an expression to be parsed.
     */
    public void processFile( String fileName )
    {
        File _file = null;
        Scanner scanner1 = null;
        
        try 
        {
            _file = new File (fileName);
            scanner1 = new Scanner(_file);
        }
        
        catch( Exception ex )
        {
            System.err.println( "FILE NOT FOUND: " + fileName );
            return;
        }
        
        while( scanner1.hasNextLine() ) 
        {
            String scanner = scanner1.nextLine();
            
            
            if( scanner.startsWith("@") ) 
            {
                if( scanner.startsWith("@print") ) 
                    print( scanner );
                
                else if( scanner.startsWith("@lookup") )           
                    lookUp( scanner );
            }
            
            else if( scanner.startsWith("#") )
                System.out.println(scanner);
            
            else 
            {
                String processedline = processLine( scanner );
                System.out.println( processLine(scanner) );
            }
        }
    }      
    
    
    
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line -- it shouldn't matter whether it comes from
     * the file or the popup dialog box. It might be convenient to return
     * return something to the caller in the form of a String that can
     * be printed or displayed.
     */
    public String processLine( String line )
    {
        
        StringTokenizer stk = new StringTokenizer( line.trim() );     
        
        String a = "";
        String s = "";
        
        theOperator.add( "*" );
        theOperator.add( "/" );
        theOperator.add( "%" );
        theOperator.add( "=" );
        theOperator.add( "-" );
        theOperator.add( "+" );
        
        
        while( stk.hasMoreTokens() )
        {
            boolean isNumber = true;
            s = stk.nextToken();  
            
            try 
            {
                value = Float.parseFloat( s );
                theStack.push( new NumberNode( value ) );
                a += "@"+ s;           
            }
            
            catch (NumberFormatException e) 
            {
                isNumber = false;         
            }
            
            // not number
            if( !isNumber ) 
            {
                
                if( theOperator.contains( s ) ) 
                {
                    
                    if( s == ( "(" ) )
                    {
                        theOperatorStack.push( s );
                    }
                    
                    else if ( s == ( ")" ) )
                    {
                        while( theOperatorStack.peek() != "(" ) 
                        {
                            pushOpNode( theOperatorStack.pop() ); 
                        }
                        
                        theOperatorStack.pop();
                    }
                    
//          else if( s[0] == ( "@" ) ) //first element of string = s[0]
//          {
//              theStack.push( new VariableNode( s , symbolTable ) );
//          }
                    else
                    {
                        while( !theOperatorStack.empty() && 
                              prec( theOperatorStack.peek() ) >= prec( s ) )
                        {
                            if( theOperatorStack.size() == theStack.size() )
                            {
                                System.err.println( "ERROR: missing operands" );
                                break;
                            }
                            
                            if( theOperatorStack.size() > theStack.size() - 1)
                            {
                                System.err.println( "ERROR: operators" );
                                break;
                            }
                            
                            
                            if( theOperatorStack.size() < theStack.size() - 1 )
                            {
                                System.err.println( "ERROR: many Operands" );
                                break;
                            }
                            
                            else
                                pushOpNode( theOperatorStack.pop() );
                            //System.out.println( " STACK CHECK " );
                        }
                        
                        theOperatorStack.push( s ); 
                    }
                    
                    a += "<" + s + ">";
                    //System.out.println( " STACK CHECK " );
                }
                
                
                else
                {
                    //System.out.println( " c " );
                    if( Character.isJavaIdentifierStart( s.charAt( 0 ) ) == false )
                    {
                        a += " NOT LEGAL TOKEN ";
                        break;
                    }
                    
                    else 
                    {
                        //System.out.println( " STACK in else " );
                        for( int i = 1; i < line.length(); i++ )
                        {
                            //System.out.println( " STACK CHECK" );
                            
                            if( !Character.isJavaIdentifierPart( line.charAt( i ) ) )
                            {
                                //System.out.println( " STACK CHECK" );
                                break;
                            }
                        }
                    }
                    System.out.println(" stack push " + theStack.size() );
                    theStack.push( new VariableNode( s , symbolTable ) );
                    a += "@"+ s; 
                }
            }
            if( stk.hasMoreTokens() )
                
            {
                a += ", ";
            }
        }
        //////////////////////////////////////////////////////////////////////////
        
        
        
        //////////////////////////////////////////////////////////////////////////
        
        while( !theOperatorStack.empty() )
        {
            if( theOperatorStack.size() == 1 && theStack.size() == 1 )
            {
                System.err.println( "ERROR: lack for one oprand" );
                break;
            }
            
            else if( theOperatorStack.size() == 0 && theStack.size() == 2 )
            {
                System.err.println( "ERROR: lack for one operator" );
                break;
            }
            
            else if( theOperatorStack.size() > theStack.size() - 1)
            {
                System.err.println( "ERROR: Too Much operators" );
                break;
            }
            
            else if( theOperatorStack.size() == theStack.size() )
            {
                System.err.println( "ERROR: lack for one oprand" );
                break;
            }
            
            else if( theOperatorStack.size() < theStack.size() - 1 )
            {
                System.err.println( "ERROR: Too Much Operands" );
                break;
            }
            
            else
            {
                System.out.println(" before pushOpNode " + theStack.size() );
                pushOpNode( theOperatorStack.pop() );
            }
        }
        //System.out.println(" here " + theStack.size() );
        
        treeRoot = theStack.pop();
        float results = treeRoot.eval();
        System.out.println( "The result is:  " + results );
        System.out.println( "****** Here is the Expression of the Tree ***** " );
        printTree( treeRoot, 0 );
        
        return a;
        
        
    }
    
    
    ///////////////////////////////////////////////////////////////////////
    
    
    
    //------------- method ---------------//
    
    private void print(String donotneedthisline) 
    {
        System.out.println(donotneedthisline);
    }
    
    private void lookUp(String lokkupthisline) 
    {
        System.out.println(lokkupthisline);
    }
    
    private void printTree( TreeNode _TreeNode, int _Depth )
    {
        for( int i = 0; i < _Depth; i++ )
        {
            System.out.print( "      " );
        }
        if( _TreeNode instanceof VariableNode || _TreeNode instanceof NumberNode )
        {      
            System.out.println( _TreeNode.cur );
        }
        else
        {     
            System.out.println( _TreeNode.cur );
            printTree( ( (OperatorNode)_TreeNode).right, _Depth + 1 );
            printTree( ( (OperatorNode)_TreeNode).left, _Depth + 1 );     
        }
    }
    
    private void pushOpNode( String operator )
    {
        
        TreeNode left = theStack.pop();
        TreeNode right = theStack.pop();
        theOperatorNode = new OperatorNode( operator, left, right );
        theStack.push( theOperatorNode );
    }
    
    private int prec( String operator )
        
    {
        
        if( operator == ("+") || operator == ("-") )
            return 2;
        else if( operator == ("*") || operator == ("/") || operator == ( "%" ))
            return 3; 
        else
            return 1;
        
        
        
    }
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get a file name interactively, then 
     * go into a loop prompting for expressions to be entered one
     * at a time.
     */
    public void interactive()
    {
        JFileChooser fChooser = new JFileChooser( "." );
        fChooser.setFileFilter( new TextFilter() );
        int choice = fChooser.showDialog( null, "Pick a file of expressions" );
        if ( choice == JFileChooser.APPROVE_OPTION )
        {
            File file = fChooser.getSelectedFile();
            if ( file != null )
                processFile( file.getName() );
        }
        
        String prompt = "Enter an arithmetic expression: ";
        String expr = JOptionPane.showInputDialog( null, prompt );
        while ( expr != null && expr.length() != 0 )
        {
            String result = processLine( expr );
            JOptionPane.showMessageDialog( null, expr + "\n" + result );
            expr = JOptionPane.showInputDialog( null, prompt );
        }
    }
    
    //+++++++++++++++++++++++++ inner class +++++++++++++++++++++++++++++++
    //---------------------------- TextFilter -----------------------------
    /**
     * This class is used with FileChooser to limit the choice of files
     * to those that end in *.txt
     */
    public class TextFilter extends javax.swing.filechooser.FileFilter
    {
        public boolean accept( File f ) 
        {
            if ( f.isDirectory() || f.getName().matches( ".*txt" ) )
                return true;
            return false;
        }
        public String getDescription()
        {
            return "*.txt files";
        }
    }
    //--------------------- main -----------------------------------------
    public static void main( String[] args )
    {
        Interpreter app = new Interpreter( args );
    }
}