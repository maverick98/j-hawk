/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

/**
 *
 * @author manosahu
 */
public enum NodeDiscoveryEnum {
    NOT_YET_DISCOVERED("Not yet discovered", "This is not yet discovered."),
    DISCOVERED("Discovered", "This is just discovered. But we do not know what lies underneath"),
    EXPLORED("Explored", "We have good knowldge about this node.");

    private String name;

    private String description;

    NodeDiscoveryEnum(String name, String desc) {
        this.name = name;
        this.description = desc;
    }

    public static NodeDiscoveryEnum parse(String name) {

        for (NodeDiscoveryEnum nodeDiscoveryEnum : NodeDiscoveryEnum.values()) {
            if (nodeDiscoveryEnum.getName().equals(name)) {
                return nodeDiscoveryEnum;
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

    public static String allNodeExplorations() {

        StringBuilder sb = new StringBuilder();
        NodeDiscoveryEnum[] all = NodeDiscoveryEnum.values();
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
