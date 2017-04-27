/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.commons.file.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.commons.logger.ILogger;
import org.commons.logger.LoggerFactory;
import org.commons.reflection.ClazzUtil;
import org.commons.string.StringUtil;

/**
 *
 * @author manosahu
 */
public class ExcelFileUtil {

    private static final ILogger logger = LoggerFactory.getLogger(ExcelFileUtil.class.getName());

    public static void main(String args[]) {
        Person person = new Person();
        List<Person> persons = readExcel("d:\\gitn\\abc.xls", Person.class);
        System.out.println(persons);
    }

    public static <T> List<T> readExcel(String excelFile, Class<T> type) {
        FileInputStream file = null;
        List<T> result = new ArrayList<T>();
        try {

            file = new FileInputStream(new File(excelFile));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            Map<String, ColumnContainer> headerPosMap = new HashMap<String, ColumnContainer>();
            Field fields[] = type.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    String header = field.getName();
                    boolean ignore = false;
                    String preSet = null;
                    String postSet = null;
                    if (field.isAnnotationPresent(ExcelColumn.class)) {
                        ExcelColumn column = (ExcelColumn) field.getAnnotation(ExcelColumn.class);
                        header = column.header();
                        ignore = column.ignore();
                        preSet = column.preSet();
                        postSet = column.postSet();
                    }
                    if (!ignore) {
                        ColumnContainer columnContainer = new ColumnContainer(header, field.getType(), -1);
                        columnContainer.setPreSet(preSet);
                        columnContainer.setPostSet(postSet);
                        headerPosMap.put(header, columnContainer);
                    }
                }
            } else {
                return null;
            }
            if (headerPosMap.isEmpty()) {
                return null;
            }
            boolean headerDetected = false;
            Map<Integer, ColumnContainer> posHeaderMap = new HashMap<Integer, ColumnContainer>();
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                Iterator<Cell> cellItr = row.cellIterator();
                int i = 1;
                T rowInstance = null;
                try {
                    rowInstance = (T) ClazzUtil.instantiate(type);
                } catch (Exception ex) {
                    logger.error(ex);
                    return null;
                }
                while (cellItr.hasNext()) {
                    Cell cell = cellItr.next();
                    //System.out.print(cell.getStringCellValue());
                    String data = cell.getStringCellValue();

                    ColumnContainer columnContainer = headerPosMap.get(data);
                    if (!headerDetected) {
                        if (headerPosMap.containsKey(data)) {

                            columnContainer.setPos(i);
                            headerPosMap.put(data, columnContainer);
                            posHeaderMap.put(i, columnContainer);
                        }
                    } else {
                        ColumnContainer columnContainer1 = posHeaderMap.get(i);
                        String fieldName = columnContainer1.getHeader();
                        String preSetMethodStr = columnContainer1.getPreSet();
                        if (!StringUtil.isNullOrEmpty(preSetMethodStr)) {
                            data = (String) ClazzUtil.invoke(rowInstance, preSetMethodStr, new Class[]{Object.class}, new Object[]{data});

                        }
                        String postSetMethodStr = columnContainer1.getPostSet();

                        String setterMethodStr = ClazzUtil.getSetterMethod(fieldName);

                        ClazzUtil.invoke(rowInstance, setterMethodStr, new Class[]{columnContainer1.getFieldType()}, new Object[]{data});
                        if (!StringUtil.isNullOrEmpty(postSetMethodStr)) {
                            ClazzUtil.invoke(rowInstance, postSetMethodStr, new Class[]{}, new Object[]{});
                        }
                    }
                    i = i + 1;
                }
                if (!headerDetected) {
                    int headerDectionCount = 0;
                    for (Entry<String, ColumnContainer> entry : headerPosMap.entrySet()) {
                        if (entry.getValue().getPos() != -1) {
                            headerDectionCount++;
                        }
                    }
                    if (headerPosMap.size() == headerDectionCount) {
                        headerDetected = true;
                    } else {
                        return null;
                    }
                } else {
                    result.add(rowInstance);
                }
                //System.out.println();

            }

        } catch (FileNotFoundException ex) {
            logger.error("could not find the file {" + excelFile + "}", ex);
        } catch (IOException ex) {
            logger.error("error while reading  the file {" + excelFile + "}", ex);
        } catch (Exception ex) {
            Logger.getLogger(ExcelFileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException ex) {
                logger.error("error while closing file ", ex);
            }
        }
        return result;
    }
}
