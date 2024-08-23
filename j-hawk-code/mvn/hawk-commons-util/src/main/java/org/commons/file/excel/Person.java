/*
 *     Manoranjan Sahu
 *  
 */
package org.commons.file.excel;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author manosahu
 */
public class Person {

    @ExcelColumn(header = "id")
    private String id1;
    @ExcelColumn(header = "name")
    private String name;
    
    @ExcelColumn(header = "address",ignore = true)
    private String address;
    
    @ExcelColumn(header = "orgIds",preSet = "beforeOrgIdsSet",postSet = "afterOrgIdsSet")
    private String orgIds;
    
    @ExcelColumn(header = "orgIdSet",ignore = true)
    private Set<String> orgIdSet = new HashSet<>();
    
    public  String beforeOrgIdsSet(Object input){
        return input+",testing";
    }
    public void afterOrgIdsSet(){
        StringTokenizer stringTokenizer = new StringTokenizer(this.getOrgIds(), ",");
        while(stringTokenizer.hasMoreTokens()){
          this.getOrgIdSet().add(stringTokenizer.nextToken());
        }
    }
    public String getAddress() {
        return address;
    }

    public String getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public Set<String> getOrgIdSet() {
        return orgIdSet;
    }

    public void setOrgIdSet(Set<String> orgIdSet) {
        this.orgIdSet = orgIdSet;
    }
    

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id1;
    }

    public void setId(String id) {
        this.id1 = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" + "id1=" + id1 + ", name=" + name + ", address=" + address + ", orgIds=" + orgIds + ", orgIdSet=" + orgIdSet + '}';
    }

   
   

    
}
