/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */

package org.hawk.html;

import java.util.Set;

/**
 * This is a placeholder for Select tag in html
 * @see Option
 * @author msahu
 */
public class Select {


    private String name;

    private Set<Option> options;

    public Select() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public Select(String name, Set<Option> options) {
        this.name = name;
        this.options = options;
    }

    @Override
    public String toString() {
        return "[Select={"+getName()+"}"+",options={"+getOptions()+"}]";
    }

    public Option findOptionByCaption(String caption)
    {
        Option rtn= null;

        for(Option option:getOptions() )
        {
            if(option.getCaption().equalsIgnoreCase(caption))
            {
                rtn = option;
                break;
            }

        }

        return rtn;
    }
}
