

package org.hawk.module.script.type;

import org.hawk.exception.HawkException;
import org.hawk.util.HawkUtil;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class StringDataType extends AbstractDataType{

    private String data;
    
    public StringDataType(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return HawkUtil.convertHawkStringToJavaString(this.data);
    }

    

    
    public IDataType copy() throws HawkException {
        IDataType clonedCopy = new StringDataType(this.data);
        return clonedCopy;
    }

   
    
    

}




