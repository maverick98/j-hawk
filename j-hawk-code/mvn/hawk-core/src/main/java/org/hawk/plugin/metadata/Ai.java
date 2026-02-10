package org.hawk.plugin.metadata;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ai {

    @XmlElement(name = "tool")
    private List<Tool> tool = new ArrayList<>();

    public List<Tool> getTool() {
        return tool;
    }

    public void setTool(List<Tool> tool) {
        this.tool = tool;
    }
}
