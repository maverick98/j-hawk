/*
 * This file is part of j-hawk
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * END_HEADER
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.commons.ds.exp;

/**
 *
 * @author manosahu
 */
public interface IObject {

    IObject add(IObject otherObject) throws Exception;

    IObject subtract(IObject otherObject) throws Exception;

    IObject multiply(IObject otherObject) throws Exception;

    IObject divide(IObject otherObject) throws Exception;

    IObject modulus(IObject otherObject) throws Exception;

    IObject equalTo(IObject otherObject) throws Exception;

    IObject greaterThan(IObject otherObject) throws Exception;

    IObject lessThan(IObject otherObject) throws Exception;

    IObject greaterThanEqualTo(IObject otherObject) throws Exception;

    IObject lessThanEqualTo(IObject otherObject) throws Exception;

    IObject and(IObject otherObject) throws Exception;

    IObject or(IObject otherObject) throws Exception;

    String toUI();

}
