/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.common.bean;

/**
 *
 * @author reemeeka
 */
@MappingBean(name=EntityObject.class)
public class DomainObjectWthCorrectMappingBeanAnnotation {
    
    
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
