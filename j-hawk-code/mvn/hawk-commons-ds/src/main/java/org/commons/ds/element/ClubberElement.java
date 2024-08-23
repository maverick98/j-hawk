/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 *  
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
package org.commons.ds.element;

import java.util.List;
import org.common.di.ScanMe;
import org.commons.ds.stack.Stack;



/**
 *
 * @author user
 */
@ScanMe(true)
public class ClubberElement extends Element {

    public ClubberElement() {
    }

    public ClubberElement(final String element, final int pos) {
        super(element, pos);
    }

    public static ClubberElement create(final String club, final int pos) {

        ClubberElement clubberElement = new ClubberElement(club, pos);

        return clubberElement;

    }

    /**
     * This checks for "("
     *
     * @return returns true if the element is "(" else false
     */
    public boolean isStartingClubber() {
        return  this.getElement().equals("(");
    }

    /**
     * This checks for ")"
     *
     * @return returns true if the element is ")" else false
     */
    public boolean isClosingClubber() {
        return  !this.isStartingClubber();
    }

    /**
     * This flips "(" into ")" and vice versa.
     *
     * @return true if it is able to flip else false
     */
    public boolean flip() {
        //if (this.isClubber()) {
            if (element.equals("(")) {
                element = ")";
            } else if (element.equals(")")) {
                element = "(";
            }
            return true;
       // }

        //return false;
    }

    @Override
    public int getShiftLength() {
        return this.getElement().length();
    }

    @Override
    public IElement parse(String eleStr, int pos) {

        IElement result = null;
        if (eleStr.startsWith("(") || eleStr.startsWith(")")) {
            result = ClubberElement.create(eleStr, pos);
        }
        return result;
    }

    /*
    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return false;

    }
    */

    @Override
    public String getHorizontalParserSequence() {
        return "2";
    }

    @Override
    public void onPostfixVisit(Stack<IElement> stack, List<IElement> postfix) {
        if (this.isStartingClubber()) {
            stack.push(this);
        } else if (this.isClosingClubber()) {
            IElement top = stack.top();

            while (!(top instanceof  ClubberElement && ((ClubberElement) top).isStartingClubber())) {
                stack.pop();
                postfix.add(top);
                top = stack.top();

            }
            stack.pop();
        }
    }

    @Override
    public void onReverseVisit(List<IElement> result , int j){
         this.flip();
         result.add(j, this);
     }

   


}
