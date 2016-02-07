

package org.hawk.module.script.type;

import org.hawk.exception.HawkException;

/**
 *
 * @version 1.0 20 Jul, 2010
 * @author msahu
 */
public class BooleanDataType extends AbstractDataType{

    private boolean data;
    public BooleanDataType(boolean data){
        this.data = data;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
    @Override
    public String toString(){
        return (this.data ?1:0)+"";
    }
    
    


    public IDataType copy() throws HawkException {
        IDataType clonedCopy = new BooleanDataType(this.data);
        return clonedCopy;
    }

    

   

}




