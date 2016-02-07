/*
 *  Copyleft(BigBang<-->BigCrunch)  Manoranjan Sahu
 *  
 */
package org.commons.file.excel;

import java.util.Objects;

/**
 *
 * @author manosahu
 */
public class ColumnContainer {

    private String header;
    
    private Class<?> fieldType;

    private Integer pos;
    
    private String preSet;
    
    private String postSet;

    public String getPreSet() {
        return preSet;
    }

    public void setPreSet(String preSet) {
        this.preSet = preSet;
    }

    public String getPostSet() {
        return postSet;
    }

    public void setPostSet(String postSet) {
        this.postSet = postSet;
    }
    
    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }
    
    public ColumnContainer(){
    
    }
    
    public ColumnContainer(String header,Class<?> fieldType, Integer pos){
        this.header = header;
        this.fieldType = fieldType;
        this.pos = pos;
       
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
    
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

 
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.header);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnContainer other = (ColumnContainer) obj;
        if (!Objects.equals(this.header, other.header)) {
            return false;
        }
        return true;
    }
    
}
