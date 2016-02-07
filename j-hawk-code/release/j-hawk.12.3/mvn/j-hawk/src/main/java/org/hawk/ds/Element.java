

package org.hawk.ds;

import org.hawk.exception.HawkException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hawk.module.script.ExecFunctionScript;
import org.hawk.module.script.ExecModuleScript;
import org.hawk.module.script.IScript;
import org.hawk.pattern.PatternMatcher;
import org.hawk.util.HawkUtil;
import java.util.Map;
import java.util.HashMap;
/**
 * This is a placeholder for perspective element in the input string.
 * @version 1.0 14 Apr, 2010
 * @author msahu
 */
public  class Element implements Comparable{
    /**
     * Raw element data.
     */
    protected String element;
    /**
     * Starting position of raw element data.
     */
    protected int pos;
    /**
     * Boolean to indiate if the element is variable.
     */
    protected boolean isVariable;

    /**
     * Boolean if this element is a exec function script.
     */
    protected boolean execFunction = false;

    /**
     * Boolean if this element is a exec module script.
     */
    protected boolean execModule = false;
    

    /**
     * Boolean if this element is a exec module script.
     */
    protected boolean structAsgnmnt = false;


    protected IScript value = null;


    private static final Map<String,List<Element>> elementCache = new HashMap<String,List<Element>>();

    public IScript getValue() {
        return value;
    }

    public void setValue(IScript value) {
        this.value = value;
    }


    
    

    public String getElement(){return this.element;}

    public void setElement(String element) {
        this.element = element;
    }
    
    public int getPos(){return pos;}

    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     *
     * @param element element
     * @param pos starting position of element
     */
    protected Element(final String element , final int pos){
            this.element=element;
            this.pos=pos;

    }
    /**
     * If this is a operator
     * @return returns <tt>true</tt>  if this is an operator else <tt>false</false>.
     */
    public boolean isOperator()
    {
        //return HawkDSConstant.OPERATORS.contains(element+"");
        return OperatorEnum.matchesExactly(element)!= null;

    }

    /**
     * This checks for "("
     * @return returns true if the element is "(" else false
     */
    public boolean isStartingClubber(){return this.isClubber() && element.equals("(");}

    /**
     * This checks for ")"
     * @return returns true if the element is ")" else false
     */
    public boolean isClosingClubber(){return this.isClubber() & !this.isStartingClubber();}


    /**
     * This flips "(" into ")" and vice versa.
     * @return true if it is able to flip else false
     */
    public boolean flip(){
        if(this.isClubber()){
            if (element.equals("(")){
                element=")";
            }else  if(element.equals(")")){
                element="(";
            }
            return true;
        }

        return false;
    }

    /**
     * This checks for the element is either "(" or ")"
     * @return returns true if element is either "(" or ")"
     */
    public boolean isClubber(){
        return element.equals("(") ||element.equals(")")
                                        ?
                                                                true
                                                        :
                                                                false
                                        ;
    }

    private static final Map<String,Boolean> operandCache = new HashMap<String,Boolean>();

    private static final Map<String,Boolean> clubberCache = new HashMap<String,Boolean>();

    private static final Map<String,Boolean> operatorCache = new HashMap<String,Boolean>();
    
    /**
     * This checks if the element is operand
     * @return returns true if the element is
     */
    public boolean isOperand(){

        if(operandCache.containsKey(this.element)){
            return operandCache.get(this.element);
        }
        boolean result =  isClubber()
                                ?
                                        false
                                    :
                                        !isOperator()
                                ;

       operandCache.put(element, result)  ;
       return result;
    }

    /**
     * This checks if the the element is variable... yet to be computed.
     * @return true if the element is variable.
     */
    public boolean isIsVariable() {
        return isVariable;
    }

    public void setIsVariable(boolean isVariable) {
        this.isVariable = isVariable;
    }

    public boolean isExecFunction() {
        return execFunction;
    }

    public void setExecFunction(boolean execFunction) {
        this.execFunction = execFunction;
    }

    public boolean isExecModule() {
        return execModule;
    }

    public boolean isExec(){
        return this.isExecFunction() || this.isExecModule();
    }

    public void setExecModule(boolean execModule) {
        this.execModule = execModule;
    }

    public boolean isStructAsgnmnt() {
        return structAsgnmnt;
    }

    public void setStructAsgnmnt(boolean structAsgnmnt) {
        this.structAsgnmnt = structAsgnmnt;
    }

    
    
    
    public boolean isVariable(){

        return this.isOperand() && this.isVariable;
    }

    public boolean isConstant(){
        return this.isOperand() && !this.isVariable;
    }

    public boolean isDouble(){

        if(this.isConstant()){
            try{
                Double.parseDouble(this.element);
            }catch(NumberFormatException nfe){
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isString(){
        return (this.isConstant() && HawkUtil.isStringDataType(this.element));
    }

    public static Element getElement(final int pos){

        return null;
		
    }

	

    @Override
    public final int hashCode(){return pos;}

    @Override
    public String toString(){return ""+element;}

    @Override
    public final boolean equals(Object herElement){
        Element myElement = this;
        return herElement==null || !(herElement instanceof Element)
                        ?
                                                false
                                        :
                                                myElement.hashCode()== ((Element)herElement).hashCode()
                        ;
    }

    public int compareTo(Object herElement){
        Element myElement = this;
        return herElement==null || !(herElement instanceof Element)
                        ?
                                                -1
                                        :
                                                myElement.hashCode()- ((Element)herElement).hashCode()
                        ;
    }

    /**
     * This checks if the element's position left to the other element
     * @param herElement the input element which is to be checked with this element for position
     * @return returns true if the element's position is left to the other element
     */
    public boolean isLeft(Element herElement){return this.hashCode()<herElement.hashCode();}

    /**
     * This checks if the element's position right to the other element
     * @param herElement the input element which is to be checked with this element for position
     * @return returns true if the element's position is right to the other element
     */
    public boolean isRight(Element herElement){return !this.isLeft(herElement);}


    /**
     * RegEx  to parse variable in the expression.
     */
    public static final Pattern VARIABLE_PATTERN=   Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(\\[?\\s*(.*)\\s*\\]?)\\s*");


    /**
     * RegEx  to parse variable in the expression.
     */
    public static final Pattern VARIABLE_EXISTENCE_PATTERN=   Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\.|\\d]*)\\s*.*");


    /**
     * RegEx  to parse variable in the expression.
     */
    public static final Pattern ARRAY_EXISTENCE_PATTERN=   Pattern.compile("([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*(\\[\\s*(.*)\\s*\\])\\s*.*");


    /**
     *RegEx to find assignment operation present in the expression
     */
    private static final Pattern ASSIGNMENT_PATTERN=Pattern.compile("(?<!=)=(?!=)(.*)");

    /**
     * RegEx to catch exec statements in the expression.
     */
    public  static final Pattern EXEC_PATTERN= Pattern.compile("\\{\\s*exec\\s*.*");


    

    /**
     * RegEx for parsing numbers
     */
    private static final Pattern CONSTANT_NUMBER_PATTERN= Pattern.compile("(\\d*\\.{0,1}\\d*).*");

    private static Map<String,String>   equationCache = new HashMap<String,String>();
    public static void main(String args[]) throws Exception{

        String equation = "`exec test (  `exec test(\"India\")`)`";
        equation = preProcessEquation(equation);
        System.out.println(equation);
        //System.out.println(parseElements(equation));
        
    }

    private static String preProcessEquation(String equation) throws HawkException{
        if(equationCache.containsKey(equation)){
            return equationCache.get(equation);
        }
        String result = null;

        result = equation;

        

        do{
            Matcher mEqnMatcher = ARRAY_EXISTENCE_PATTERN.matcher(result);
            if(mEqnMatcher.find()){
                
                String arrayBracketData = HawkUtil.parseDelimeterData(result.substring(mEqnMatcher.start(2)),'[',']');
                
                if( arrayBracketData != null && !arrayBracketData.isEmpty()){
                    String preEqn = result.substring(0,mEqnMatcher.start(2));
                    String postEqn = result.substring
                                                (
                                                    result.indexOf
                                                                (
                                                                    ']',
                                                                    mEqnMatcher.start(2)+arrayBracketData.length()+1
                                                                )+1
                                                );
                    StringBuilder sb = new StringBuilder();
                    sb.append(preEqn);
                    sb.append(OperatorEnum.ARRAYBRACKET.getOperator());//temporary internal representation for []
                    sb.append("(");
                    sb.append(arrayBracketData);
                    sb.append(")");
                    sb.append(postEqn);
                    result = sb.toString();

                }else{
                    break;
                }

            }else{
              break;
            }
        }while (true);
        result =  result.replaceAll("\\)\\s*`", ")}");
        result =  result.replaceAll("`", "{");
        if(!result.startsWith("\"")){//Hack fix me 
            Matcher asgnmntMatcher = ASSIGNMENT_PATTERN.matcher(result);
            if(asgnmntMatcher.find()){
                String pre = result.substring(0,asgnmntMatcher.start(1));
                String post = result.substring(asgnmntMatcher.start(1));
                result = pre+"("+post+")";

            }
        }
        equationCache.put(equation, result);
        return result;
    }



    /**
     * This parses input equation into list of Element
     * @param equation input equation to be parsed.
     * @return returns list of element
     * @throws HawkException
     */
    public static List<Element> parseElements(String equation) throws HawkException{

        List<Element> elements = new ArrayList<Element>();
        equation = preProcessEquation(equation);

        int operatorCount=0;
        int inputCount=0;
        int pos = 0;
        for(int i=0;i<equation.length();){
            char ele = equation.charAt(i);
            String eleStr = ele+"";
            Element element = null;
            String subStr = equation.substring(i);
            OperatorEnum operatorEnum = null;
            if((operatorEnum = OperatorEnum.matches(subStr))!= null){
                element = new Element(operatorEnum.getOperator(), pos++);
                i+=operatorEnum.getOperator().length();
                operatorCount++;
            }else if (eleStr.equals("(") || eleStr.equals(")")){

                element = new Element(eleStr,pos++);
                i++;
            }else if(eleStr.trim().equals("")){
                i++;
                continue;
            }else{
                
                Matcher mExecMatcher = EXEC_PATTERN.matcher(subStr);
                
                if(mExecMatcher.matches()){
                    String operand = parseExec(subStr);
                    if(PatternMatcher.match(ExecFunctionScript.getPattern(), operand)!=null){
                        element = new Element(operand,pos++);
                        element.setIsVariable(true);
                        element.setExecFunction(true);
                        element.setExecModule(false);
                        element.setStructAsgnmnt(false);
                    }else if(PatternMatcher.match(ExecModuleScript.getPattern(), operand)!=null){
                        element = new Element(operand,pos++);
                        element.setIsVariable(true);
                        element.setExecFunction(false);
                        element.setExecModule(true);
                        element.setStructAsgnmnt(false);
                    }else{
                        throw new HawkException("invalid exec script...");
                    }
                    i += operand.length()+2;
                    inputCount++;
                }else {
                    String operand = HawkUtil.parseDelimeterData(subStr, '\"', '\"');
                    if(operand != null){
                        element = new Element(operand,pos++);
                        element.setIsVariable(false);
                        i += operand.length()+2;
                        inputCount++;
                    }else{

                        mExecMatcher = VARIABLE_EXISTENCE_PATTERN.matcher(subStr);
                        if(mExecMatcher.matches()){
                                operand = mExecMatcher.group(1);
                                element = new Element(operand,pos++);
                                element.setIsVariable(true);
                                i += operand.length();
                                inputCount++;
                        }else{
                                mExecMatcher = CONSTANT_NUMBER_PATTERN.matcher(subStr);
                                if(mExecMatcher.matches()){
                                    operand = mExecMatcher.group(1);
                                    if(operand == null || operand.isEmpty()){
                                        throw new HawkException("invalid expression");
                                    }
                                    element = new Element(operand,pos++);
                                    element.setIsVariable(false);
                                    i += operand.length();
                                    inputCount++;
                                }
                       }
                    }
               }

            }
            elements.add(element);

        }

            return inputCount==operatorCount+1
                                                    ?
                                                            elements
                                                        :
                                                            null
                                                    ;

    }

    /**
     * This parses the "`exec test (  `exec test("India")`)`"
     * @param input
     * @return returns "exec test (  `exec test("India")`)"
     * @throws HawkException
     */
    private static String parseExec(String input ) throws HawkException{
        if(input ==null || input.isEmpty()){
            throw new HawkException("invalid");
        }
        input  = input.trim();
        
        return HawkUtil.parseDelimeterData(input, '{','}');
    }


    /**
     * This returns the input list of elements . This does not
     * modify the input list.
     * @param list list of elements to be reversed.
     * @return returns the reverse of the list.
     */
    public static List<Element> reverse(final List<Element> list){
        List<Element> result = new ArrayList<Element>();

        for(int i=list.size()-1,j=0;i>=0;i--,j++){
            Element element = (Element)list.get(i);

            if(element.isClubber()){

                element.flip();
                result.add(j,element);
            }else{
                result.add(j,list.get(i));
            }
        }


        return result;
    }

    /**
     * This returns the precedence of operators supported in hawk.
     * @return
     * @throws HawkException
     */
    public int getPrecedence() throws HawkException{
        

        if(!this.isOperator()){
            throw new HawkException("Not an operator");
        }
        return OperatorEnum.value(element).getPrecedence();
        
    }

   public boolean hasHigherPrecedence(Element otherOpr) throws HawkException{
       if(!this.isOperator() || !otherOpr.isOperator()){
           throw new HawkException("Not an operator");
       }
       boolean result = false;
       OperatorEnum opr1 = OperatorEnum.value(this.getElement());
       OperatorEnum opr2 = OperatorEnum.value(otherOpr.getElement());
       if(opr1.equals(OperatorEnum.ARRAYBRACKET) && opr2.equals(OperatorEnum.REFERENCE)){
           result = this.getPos()<otherOpr.getPos();
       }else{
           result = this.getPrecedence() >= otherOpr.getPrecedence();
       }

       return result;
   }

}




