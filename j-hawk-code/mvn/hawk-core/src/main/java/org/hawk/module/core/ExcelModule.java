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
package org.hawk.module.core;


import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.common.di.AppContainer;
import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class ExcelModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public String getName() {
        return "ExcelModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "dumpToArray", sequence = 1, ignoreException = false, hawkParam = "var filename")
    public ArrayDeclScript dumpToArray(Object... args) throws Exception {
        String excelFile = (String)args[0];
        ArrayDeclScript arrayDeclScript = ArrayDeclScript.createDefaultArrayScript();
        FileInputStream file = new FileInputStream(new File(excelFile));
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheetAt(0);
        
        Iterator<Row> rowIterator = sheet.iterator();
//        Integer lineNo =1;
        while(rowIterator.hasNext()){
            Row row = rowIterator.next();
//            Iterator<Cell> cellItr  = row.cellIterator();
        }
        return arrayDeclScript;
    }
    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
