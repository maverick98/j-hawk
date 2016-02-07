package org.hawk.module.script;

import org.hawk.module.script.enumeration.VarTypeEnum;
import org.hawk.module.script.type.Variable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.hawk.ds.Stack;
import org.hawk.exception.HawkException;
import org.hawk.module.script.type.StringDataType;
import org.hawk.pattern.PatternMatcher;
import static org.hawk.constant.HawkLanguageKeyWord.*;

/**
 *
 * @version 1.0 19 Jul, 2010
 * @author msahu
 */
public class StructureDefnScript extends MultiLineScript {

    private static final Pattern STRUCTURE_DEFN_PATTERN =
            Pattern.compile("\\s*" + struct + "\\s*([a-z|A-Z]{1,}[a-z|A-Z|\\d]*)\\s*");
    private static final Pattern STRUCTURE_DEFN_BRACKET_START_PATTERN = Pattern.compile("\\s*\\{\\s*");
    private static final Pattern STRUCTURE_DEFN_BRACKET_END_PATTERN = Pattern.compile("\\s*\\}\\s*");
    private String structureName;
    private boolean xmlStruct = false;

    public boolean isXmlStruct() {
        return xmlStruct;
    }

    public void setXmlStruct(boolean xmlStruct) {
        this.xmlStruct = xmlStruct;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(this.getStructureName() + " members are :::");

        for (Entry<Integer, IScript> entry : this.getInnerScripts().entrySet()) {

            sb.append(entry.getValue().toString());

        }

        return sb.toString();

    }

    public Map<String, IScript> instantiate() throws HawkException {


        Map<String, IScript> structInstance = new HashMap<String, IScript>();

        for (Entry<Integer, IScript> entry : this.getInnerScripts().entrySet()) {

            IScript script = entry.getValue();

            structInstance.put(script.getVariable().getVariableName(), script.copy());
        }

        return structInstance;
    }

    /**
     * This converts the hawk structure into java map..
     * @return
     * @throws org.hawk.exception.HawkException
     */
    @Override
    public Map<Object, Object> toJavaMap() throws HawkException {

        Map<String, IScript> instance = this.instantiate();

        Map<Object, Object> javaMap = new LinkedHashMap<Object, Object>();

        for (Entry<String, IScript> entry : instance.entrySet()) {

            javaMap.put(entry.getValue().getVariable().getVariableName(), entry.getValue().getVariableValue() + "");

        }

        return javaMap;
    }

    public static boolean parseStructureContainers(Map<Integer, String> scriptMap, Map<String, StructureDefnScript> structureDefnMap) throws HawkException {



        for (int i = 0; i < scriptMap.size();) {

            Map<Integer, String> map = PatternMatcher.match(STRUCTURE_DEFN_PATTERN, scriptMap.get(i));

            if (map != null) {

                String structureName = map.get(1);
                if (structureDefnMap.containsKey(structureName)) {
                    throw new HawkException("Duplicate defn for structure {" + structureName + "}");
                }
                MultiLineContainer structureDefn = extractStructureDefinition(scriptMap, i + 1, structureName);

                StructureDefnScript structureDefnScript = new StructureDefnScript();

                structureDefnScript.setStructureName(structureName);

                structureDefnScript.setMultiLineContainer(structureDefn);

                structureDefnMap.put(structureName, structureDefnScript);

                i = structureDefn.getEnd() + 1;

            } else {

                i = i + 1;

            }

        }

        return true;
    }

    public static boolean parseStructureMap
                            (
                                String structName,
                                Map<String, Object> hawkParamMap,
                                Map<String, StructureDefnScript> structureDefnMap
                            ) throws HawkException {


        StructureDefnScript structureDefnScript = new StructureDefnScript();
        structureDefnScript.setXmlStruct(true);
        structureDefnScript.setStructureName(structName);
        int i = 1;
        for (Entry<String, Object> entry : hawkParamMap.entrySet()) {
            String param = entry.getKey();

            Object paramValue = entry.getValue();
            LocalVarDeclScript localVarDeclScript = new LocalVarDeclScript();
            Variable localVar = new Variable(VarTypeEnum.VAR, null, param);
            localVarDeclScript.setLocalVar(localVar);
            Variable localVarValue = new Variable(VarTypeEnum.VAR, null, paramValue.toString());
            localVarValue.setVariableValue(new StringDataType(paramValue.toString()));
            localVarDeclScript.setLocalVarValue(localVarValue);
            localVarDeclScript.setLocalVarValExp(paramValue.toString());
            localVarDeclScript.setOrigVariableValExp(localVarDeclScript.getLocalVarValExp());
            structureDefnScript.addScript(i++, localVarDeclScript);
        }

        structureDefnMap.put(structName, structureDefnScript);
        return true;
    }

    public static boolean parseStructuresMap(Map<String, Map<String, Object>> xmlStructMap, Map<String, StructureDefnScript> structureDefnMap) throws HawkException {

        for (Entry<String, Map<String, Object>> entry : xmlStructMap.entrySet()) {

           parseStructureMap(entry.getKey(),entry.getValue(),structureDefnMap);

        }

        return true;
    }

    public static boolean parseStructures(Map<Integer, String> scriptMap, Map<String, StructureDefnScript> structureDefnMap) throws HawkException {

        parseStructureContainers(scriptMap, structureDefnMap);
        for (Entry<String, StructureDefnScript> entry : structureDefnMap.entrySet()) {
            StructureDefnScript structureDefnScript = entry.getValue();
            if (structureDefnScript.isXmlStruct()) {
                continue;
            }
            MultiLineContainer structureDefn = structureDefnScript.getMultiLineContainer();

            for (int j = structureDefn.getStart() + 1; j < structureDefn.getEnd(); j++) {

                SingleLineScript singleLineScript = null;

                Map<Integer, String> lineLocalVarMatcherMap = PatternMatcher.match(LocalVarDeclScript.getPattern(), scriptMap.get(j));

                if (lineLocalVarMatcherMap == null) {

                    Map<Integer, String> lineStructMatcherMap = PatternMatcher.match(StructureScript.getPattern(), scriptMap.get(j));

                    if (lineStructMatcherMap != null) {

                        singleLineScript = StructureScript.createScript(lineStructMatcherMap);

                        structureDefnScript.addScript(j, singleLineScript);

                        if (singleLineScript != null) {

                            singleLineScript.setOuterMultiLineScript(structureDefnScript);

                        }

                    } else {
                        Map<Integer, String> lineArrayDeclMatcherMap = PatternMatcher.match(ArrayDeclScript.getPattern(), scriptMap.get(j));

                        if (lineArrayDeclMatcherMap != null) {

                            singleLineScript = ArrayDeclScript.createScript(lineArrayDeclMatcherMap);

                            structureDefnScript.addScript(j, singleLineScript);

                            if (singleLineScript != null) {

                                singleLineScript.setOuterMultiLineScript(structureDefnScript);

                            }

                        }
                    }

                } else {

                    singleLineScript = LocalVarDeclScript.createScript(lineLocalVarMatcherMap);

                    singleLineScript.execute();

                    structureDefnScript.addScript(j, singleLineScript);

                    if (singleLineScript != null) {

                        singleLineScript.setOuterMultiLineScript(structureDefnScript);

                    }
                }
            }
        }
        return true;
    }

    private static MultiLineContainer extractStructureDefinition(Map<Integer, String> scriptMap, int x, String structureName) throws HawkException {

        MultiLineContainer mlc = new MultiLineContainer();

        boolean firstStartBracktFound = false;

        Stack stack = new Stack();

        int start = x;

        int end = -1;

        int i = x;

        for (; i < scriptMap.size(); i++) {

            Map<Integer, String> startingBracketMap = PatternMatcher.match
                                                                      (
                                                                          STRUCTURE_DEFN_BRACKET_START_PATTERN,
                                                                          scriptMap.get(i)
                                                                      );

            if (startingBracketMap != null) {

                if (!firstStartBracktFound) {

                    firstStartBracktFound = true;

                    start = i;
                }

                stack.push("{");

            }

            Map<Integer, String> endingBracketMap = PatternMatcher.match
                                                                    (
                                                                        STRUCTURE_DEFN_BRACKET_END_PATTERN,
                                                                        scriptMap.get(i)
                                                                    );

            if (endingBracketMap != null) {

                stack.pop();

            }

            if (stack.isEmpty()) {
                break;
            }


        }

        if (!stack.isEmpty() || !firstStartBracktFound) {

            throw new HawkException("Invalid structure {" + structureName + "}");
        }

        end = i;

        mlc.setStart(start);

        mlc.setEnd(end);

        return mlc;
    }

    public boolean doesMemberExist(String structMember) {

        if (structMember == null || structMember.isEmpty()) {

            return false;

        }

        boolean status = false;

        for (Entry<Integer, IScript> entry : this.getInnerScripts().entrySet()) {

            if (entry.getValue() instanceof LocalVarDeclScript) {

                LocalVarDeclScript localVarDeclScript = (LocalVarDeclScript) entry.getValue();

                status = localVarDeclScript.getLocalVar().getVariableName().equals(structMember);

            } else if (entry.getValue() instanceof StructureScript) {

                StructureScript structureScript = (StructureScript) entry.getValue();

                status = structureScript.getStructVarName().getVariableName().equals(structMember);

            }

        }

        return status;
    }

    @Override
    public boolean isVariable() {
        return false;
    }
}
