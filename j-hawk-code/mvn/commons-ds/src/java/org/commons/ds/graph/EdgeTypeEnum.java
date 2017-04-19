/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

/**
 *
 * @author manosahu
 */
public enum EdgeTypeEnum {
    TREE("TreeEdge", "This is tree edge"),
    BACK("BackEdge", "This is back edge");

    private String name;

    private String description;

    EdgeTypeEnum(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

    public static EdgeTypeEnum parse(String name) {

        for (EdgeTypeEnum edgeTypeEnum : EdgeTypeEnum.values()) {
            if (edgeTypeEnum.getName().equals(name)) {
                return edgeTypeEnum;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String allEdgeTypeEnum() {

        StringBuilder sb = new StringBuilder();
        EdgeTypeEnum[] all = EdgeTypeEnum.values();
        for (int i = 0; i < all.length - 1; i = i + 1) {
            sb.append(all[i].getName());
            sb.append(",");
        }
        sb.append(all[all.length - 1]);
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.name;
    }

}
