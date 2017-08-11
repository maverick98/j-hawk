/*
 * This file is part of j-hawk
 * Copyright (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 *

 *
 * END_HEADER
 */
package org.commons.ds.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.common.di.ScanMe;

/**
 *
 * @author manosahu
 */
@ScanMe(true)
public class MatrixElement extends Element {

    private static final Logger logger = Logger.getLogger(MatrixElement.class.getName());

    /**
     * RegEx for parsing numbers
     */
    private static final Pattern MATRIX_PATTERN = Pattern.compile("(\\[\\s*.*\\s*\\])\\s*.*");

    public MatrixElement() {

    }

    public MatrixElement(final String element, final int pos) {
        super(element, pos);
    }

    @Override
    public int getShiftLength() {
        return this.getElement().length();
    }

    public static MatrixElement create(final String data, final int pos) {
        MatrixElement matrixElement = new MatrixElement(data, pos);

        return matrixElement;
    }

    @Override
    public IElement parse(String input, int pos) {
        Matcher matrixMatcher = MATRIX_PATTERN.matcher(input);
        Element matrixElement = null;
        if (matrixMatcher.matches()) {
            String number = matrixMatcher.group(1);
            matrixElement = create(number, pos);
        }
        return matrixElement;
    }

    @Override
    public String getHorizontalParserSequence() {
        return "4.2.2.2";
    }
    
    @Override
    public boolean shouldEvaluate(){
        return true;
    }
}
