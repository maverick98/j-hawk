package org.hawk.plugin.metadata;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Schema {

    @XmlElement(name = "field")
    private List<Field> field = new ArrayList<>();

    public List<Field> getField() {
        return field;
    }

    public void setField(List<Field> field) {
        this.field = field;
    }
}
