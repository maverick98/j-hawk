
package org.hawk.module.lib;

import org.hawk.module.annotation.Module;
import org.hawk.module.annotation.SubTask;
import org.hawk.module.script.ArrayDeclScript;
import org.hawk.module.script.IScript;
import java.util.Map.Entry;
import org.hawk.logger.HawkLogger;
import org.hawk.module.script.type.IDataType;
import org.hawk.module.script.type.StringDataType;
import org.hawk.module.script.type.Variable;
import java.util.logging.Level;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.hawk.util.HawkUtil;
import java.io.File;
import org.hawk.module.AbstractModule;
/**
 *
 * @version 1.0 25 Jan, 2011
 * @author msahu
 */
@Module(name="FileModule")
public class FileModule extends AbstractModule{

     @Override
    public String toString() {
        return this.getName();
    }
    public String getName() {
        return "FileModule";
    }
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name="read",sequence = 1,ignoreException=false ,hawkParam="var array,var filename")
    public boolean read(Object ... args) throws Exception{
        boolean status = false;
        ArrayDeclScript arrayDeclScript = (ArrayDeclScript)args[0];
        int i = 1;
        
        BufferedReader bfr = null;
        try{
             bfr = new BufferedReader(new InputStreamReader(new FileInputStream(args[1].toString())));
             String line = null;
             do{

                 line = bfr.readLine();
                 if(line!=null){
                     Variable val = arrayDeclScript.getMember(i).getVariableValue();
                     IDataType newValue = new StringDataType(line);
                     val.setVariableValue(newValue);
                     i++;
                 }
             }while(line!=null);

        }catch(Exception ex){

           logger.severe( ex.getMessage());
           return false;
        }finally{
            try{

                bfr.close();

            }catch(Exception ex1){
            logger.severe( ex1.getMessage());
            return false;
           }
        }
        
        return true;
    }

    @SubTask(name="write",sequence = 1,ignoreException=false ,hawkParam="var array")
    public boolean write(Object ... args) throws Exception{
        boolean status = false;
        ArrayDeclScript arrayDeclScript = (ArrayDeclScript)args[0];
        int i = 1;
        StringBuilder sb = new StringBuilder();
        for(Entry<Integer,IScript> entry: arrayDeclScript.getMembers().entrySet()){
            sb.append(entry.getValue().toUI()+"\n");
        }
        HawkUtil.writeFile((String)args[1], sb.toString());
        return true;
    }

    @SubTask(name="delete",sequence = 1,ignoreException=false,hawkParam="var filename")
    public boolean delete(Object ... args) throws Exception{
       boolean status = false;
       File file = new File(args[0].toString());
       if(file.exists()){
           status = file.delete();
       }else{
           status = false;
       }
       
       return status;
    }

    @SubTask(name="list",sequence = 1,ignoreException=false,hawkParam="var array,var filename")
    public boolean list(Object ... args) throws Exception{
        boolean status = false;
        ArrayDeclScript arrayDeclScript = (ArrayDeclScript)args[0];
        File file = new File(args[1].toString());
        String files[]  = file.list();
        if(files != null && files.length >0){
            for(int i=0;i<files.length;i++){
                 Variable val = arrayDeclScript.getMember(i+1).getVariableValue();
                 IDataType newValue = new StringDataType(files[i]);
                 val.setVariableValue(newValue);
                 
            }
            status =true;
        }else{
            status = false;
        }
        return status;
    }
    private static final HawkLogger logger = HawkLogger.getLogger(FileModule.class.getName());
}