package org.hawk.plugin.metadata;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tool {

    private String name;
    private String description;
    private String promptFile;
    private boolean strict;

    @XmlElement(name = "inputSchema")
    private Schema inputSchema;

    @XmlElement(name = "outputSchema")
    private Schema outputSchema;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPromptFile() {
        return promptFile;
    }

    public boolean isStrict() {
        return strict;
    }

    public Schema getInputSchema() {
        return inputSchema;
    }

    public Schema getOutputSchema() {
        return outputSchema;
    }
}
