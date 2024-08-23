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
