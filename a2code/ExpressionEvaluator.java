//Ben Aston 
//CS 445, Monday/Wednesday night lecture
//Professor Garrision
//Date last updated: 2/21/2016
//Assignment 2, Expression Evaluator

import java.util.Scanner;

public class ExpressionEvaluator {
    private static Stack operatorStack = new Stack<Character>();//Holds operators
    private  static Stack valueStack = new Stack<Double>();//Holds values
    
    //--------------------------------------------------------------------------
    //constructor
    //--------------------------------------------------------------------------
    public ExpressionEvaluator(){
        operatorStack = new Stack<Character>();
        valueStack = new Stack<Double>();
    }
    
    //--------------------------------------------------------------------------
    //evaluate: given a string in infix notation, this method calculates the
    //          value of that algebraic expression. returns a double
    //--------------------------------------------------------------------------
    public static double evaluate(String infix){
        boolean numberBuilding = false;
        char currentChar, topOperator;
        double operandOne, operandTwo;
        infix = infix.replaceAll(" ", "");
        Scanner input = new Scanner(infix);
        input.useDelimiter("");
        
        while(input.hasNext()){
            currentChar = input.next().charAt(0);
            System.out.println("Current character: " + currentChar);
            if(currentChar == '^'){
                operatorStack.push(currentChar);
                numberBuilding = false;
            }else if(currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/'){
                while(!operatorStack.isEmpty() && getPrecedence(currentChar) <= getPrecedence((Character)operatorStack.peek()) ){
                    topOperator = (char)operatorStack.pop();
                    operandOne = (double)valueStack.pop();
                    operandTwo = (double)valueStack.pop();
                    try{
                        valueStack.push(calculate(operandOne, operandTwo, topOperator));
                    }catch(IllegalOperatorException e){
                        System.err.println(e);
                    }
                }
                operatorStack.push(currentChar);
                numberBuilding = false;
            }else if(currentChar == '('){
                operatorStack.push(currentChar);
                numberBuilding = false;
            }else if(currentChar == ')'){
                topOperator = (char)operatorStack.pop();
                while(topOperator != '('){
                    operandOne = (double)valueStack.pop();
                    operandTwo = (double)valueStack.pop();
                    try{
                        valueStack.push(calculate(operandOne, operandTwo, topOperator));
                    }catch(IllegalOperatorException e){
                        System.err.println(e);
                    }
                    topOperator = (char)operatorStack.pop();
                }
                numberBuilding = false;
            }else if((int)currentChar >= '0' && (int)currentChar <= '9'){
                if(!numberBuilding)
                    valueStack.push(new Double((int)currentChar - 48));
                else
                    valueStack.push((double)valueStack.pop() * 10 + new Double((int)currentChar - 48));
                numberBuilding = true;
            }
            System.out.println("Operand Stack: " + valueStack);
            System.out.println("Operator Stack: " + operatorStack);
        }
        
        while(!operatorStack.isEmpty()){
            topOperator = (char)operatorStack.pop();
            operandOne = (double)valueStack.pop();
            operandTwo = (double)valueStack.pop();
            try{
                valueStack.push(calculate(operandOne, operandTwo, topOperator));
            }catch(IllegalOperatorException e){
                System.err.println(e);
            }
        }
        return (Double)valueStack.peek();
        
    }
    
    //--------------------------------------------------------------------------
    //getPrecedence: returns an integer representing an operator's precedence
    //--------------------------------------------------------------------------
    private static int getPrecedence(char c){
        if(c == '^')
            return 3;
        else if (c == '/' || c == '*')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else 
            return 0;
    }
    
    //--------------------------------------------------------------------------
    //calculate: takes two operands (double) and one operator (char) and
    //           performs the given operation
    //--------------------------------------------------------------------------
    private static double calculate(double a, double b, char operator) throws IllegalOperatorException{
        if(operator == '^')
            return Math.pow(b, a);
        else if(operator == '*')
            return a * b;
        else if(operator == '/')
            return b / a;
        else if(operator == '-')
            return b - a;
        else if(operator == '+')
            return a + b;
        throw new IllegalOperatorException();
    }
    
    //--------------------------------------------------------------------------
    //balancedBrackets: returns a boolean value dependent on whether the given
    //                  expression uses brackets legally
    //--------------------------------------------------------------------------
    public static boolean balancedBrackets(String infix){
        Scanner input = new Scanner(infix);
        Stack bracketStack = new Stack<Character>();
        boolean isBalanced = true;
        char nextChar, openDelimiter;
        
        input.useDelimiter("");
        while(isBalanced && input.hasNext()){
            nextChar = input.next().charAt(0);
            if(nextChar == '(' || nextChar == '[' || nextChar == '{')
                bracketStack.push((Character)nextChar);
            else if (nextChar == ')' || nextChar == ']' || nextChar == '}'){
                if(bracketStack.isEmpty())
                    isBalanced = false;
                else{
                    openDelimiter = (char)bracketStack.pop();
                    if((openDelimiter == '(' && nextChar == ')') || (openDelimiter == '[' && nextChar == ']') || (openDelimiter == '{' && nextChar == '}'))
                        isBalanced = true;
                    else
                        isBalanced = false;
                }
            }
        }
        if(!bracketStack.isEmpty())
                isBalanced = false;
        input.close();
        if(!isBalanced)
            System.out.println("Brackets not balanced");
        return isBalanced;
    }
    
    
    //--------------------------------------------------------------------------
    //validExpression: checks an infix expression for syntax errors such as
    //                 inbalanced brackets, multiple operands, or operators in a
    //                 row.
    //--------------------------------------------------------------------------
    public static boolean validExpression(String infix){
        if(!balancedBrackets(infix))
            return false;
        Scanner input = new Scanner(infix);
        Stack characterStack = new Stack<Character>();
        char nextChar;
        
        input.useDelimiter("");
        while(input.hasNext()){
            nextChar = input.next().charAt(0);
            if(!characterStack.isEmpty()){
                if(nextChar == ' '){
                    nextChar = input.next().charAt(0);//Ignoring spaces
                    //checking for two operands in a row
                    if((char)characterStack.peek() >= '0' && (char)characterStack.peek() <= '9' && nextChar >= '0' && nextChar <= '1'){
                        System.out.println("Cannot have two operands in a row");
                        return false;
                    }
                }
                if(((char)characterStack.peek() == '^' || (char)characterStack.peek() == '*' || (char)characterStack.peek() == '/' ||
                    (char)characterStack.peek() == '+' || (char)characterStack.peek() == '-') &&
                    (nextChar == '^' || nextChar == '*' || nextChar == '/' || nextChar == '+' || nextChar == '-')){
                    System.out.println("Cannot have two operators in a row");
                    return false;
                }
            }
            characterStack.push(nextChar);    
        }
        return true;
    }
    
    //--------------------------------------------------------------------------
    //main: provides an interface for the user to interact with
    //--------------------------------------------------------------------------
    public static void main(String[] args){
        String userExpression;
        Scanner input = new Scanner(System.in);
        
        System.out.println("enter an algebraic expression");
        userExpression = input.nextLine();

        while(!validExpression(userExpression)){
            System.out.println(userExpression + " is not valid");
            System.out.println("Enter a legal expression: ");
            userExpression = input.nextLine();
    }
        System.out.println(userExpression + " = " + evaluate(userExpression));
    }
}
