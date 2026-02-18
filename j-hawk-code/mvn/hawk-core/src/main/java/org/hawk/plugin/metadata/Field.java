package org.hawk.plugin.metadata;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {

    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
