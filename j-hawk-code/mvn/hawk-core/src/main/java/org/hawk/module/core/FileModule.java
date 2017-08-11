/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.module.core;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import org.commons.file.FileUtil;
import org.common.di.AppContainer;

import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.object.LocalVarDeclScript;
import org.hawk.lang.type.IDataType;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.logger.HawkLogger;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 25 Jan, 2011
 * @author msahu
 */
@ScanMe(true)
   
   
public class FileModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "FileModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "readData", sequence = 1, ignoreException = false, hawkParam = "var filename")
    public String readData(Object... args) throws Exception {

        String result = FileUtil.readFile(args[0].toString());
        return result;
    }

   
    @SubTask(name = "dump", sequence = 1, ignoreException = false, hawkParam = "var filename")
    public ArrayDeclScript dumpToArray(Object... args) throws Exception {

        ArrayDeclScript arrayDeclScript = ArrayDeclScript.createDefaultArrayScript();
        Map<Integer, String> fileData = FileUtil.dumpFileToMap(args[0].toString());
        if (fileData != null) {
            for (Entry<Integer, String> entry : fileData.entrySet()) {
                int lineNo = entry.getKey() + 1;
                String line = entry.getValue();
                if (line != null) {
                    LocalVarDeclScript lvds = LocalVarDeclScript.createDummyStringScript(line);
                    arrayDeclScript.setMember(lineNo, lvds);
                }

            }

        }
        return arrayDeclScript;
    }
    
     @SubTask(name = "dumpStr", sequence = 1, ignoreException = false, hawkParam = "var filename")
    public String dumpStr(Object... args) throws Exception {

        String result  =    FileUtil.readFile(args[0].toString());
       
        return result;
    }

    @SubTask(name = "writeData", sequence = 1, ignoreException = false, hawkParam = "var fileName,var data")
    public boolean writeData(Object... args) throws Exception {
        boolean status;
        String fileName = args[0].toString();
        String data = args[1].toString();
        System.out.println("writing to file " + fileName);
        status = FileUtil.writeFile(fileName, data);
        return status;
    }

    @SubTask(name = "write", sequence = 1, ignoreException = false, hawkParam = "var array")
    public boolean write(Object... args) throws Exception {
        boolean status;
        ArrayDeclScript arrayDeclScript = (ArrayDeclScript) args[0];

        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, IObjectScript> entry : arrayDeclScript.getMembers().entrySet()) {
            sb.append(entry.getValue().toUI());
            sb.append("\n");
        }
        status = FileUtil.writeFile((String) args[1], sb.toString());
        return status;
    }

    @SubTask(name = "delete", sequence = 1, ignoreException = false, hawkParam = "var filename")
    public boolean delete(Object... args) throws Exception {
        boolean status;
        File file = new File(args[0].toString());
        if (file.exists()) {
            status = file.delete();
        } else {
            status = false;
        }

        return status;
    }

    @SubTask(name = "list", sequence = 1, ignoreException = false, hawkParam = "var array,var filename")
    public boolean list(Object... args) throws Exception {
        boolean status;
        ArrayDeclScript arrayDeclScript = (ArrayDeclScript) args[0];
        File file = new File(args[1].toString());
        String files[] = file.list();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                Variable val = arrayDeclScript.getMember(i + 1).getVariableValue();
                IDataType newValue = new StringDataType(files[i]);
                val.setValue(newValue);

            }
            status = true;
        } else {
            status = false;
        }
        return status;
    }

    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
    private static final HawkLogger logger = HawkLogger.getLogger(FileModule.class.getName());
}