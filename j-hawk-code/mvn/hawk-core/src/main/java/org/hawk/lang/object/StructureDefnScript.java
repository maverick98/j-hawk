/*
 * This file is part of j-hawk
 * CopyLeft (C) 2012-2013 Manoranjan Sahu, All Rights are left.
 *
 * 
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the j-hawk maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * 
 * 
 *
 * 
 */
package org.hawk.lang.object;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.commons.ds.stack.Stack;

import org.hawk.lang.IScript;
import static org.hawk.lang.constant.HawkLanguageKeyWord.struct;
import org.hawk.lang.enumeration.VarTypeEnum;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.multiline.MultiLineScript;
import org.hawk.lang.singleline.SingleLineScript;
import org.hawk.lang.type.StringDataType;
import org.hawk.lang.type.Variable;
import org.hawk.pattern.PatternMatcher;

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

    public Map<String, IObjectScript> instantiate() throws Exception {


        Map<String, IObjectScript> structInstance = new HashMap<>();

        this.getInnerScripts().entrySet().stream().map((entry) -> (IObjectScript)entry.getValue()).forEach((script) -> {
            structInstance.put(script.getVariable().getName(), (IObjectScript)script.copy());
        });

        return structInstance;
    }

    /**
     * This converts the hawk structure into java map..
     *
     * @return
     * @throws java.lang.Exception
     * @throws org.hawk.exception.Exception
     */
    @Override
    public Map<Object, Object> toJavaMap() throws Exception {

        Map<String, IObjectScript> instance = this.instantiate();

        Map<Object, Object> javaMap = new LinkedHashMap<>();

        instance.entrySet().stream().forEach((entry) -> {
            javaMap.put(entry.getValue().getVariable().getName(), entry.getValue().getVariableValue() + "");
        });

        return javaMap;
    }

    public static boolean parseStructureContainers(Map<Integer, String> scriptMap, Map<String, StructureDefnScript> structureDefnMap) throws Exception {



        for (int i = 0; i < scriptMap.size();) {

            Map<Integer, String> map = PatternMatcher.match(STRUCTURE_DEFN_PATTERN, scriptMap.get(i));

            if (map != null) {

                String structureName = map.get(1);
                if (structureDefnMap.containsKey(structureName)) {
                    throw new Exception("Duplicate defn for structure {" + structureName + "}");
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

    public static boolean parseStructureMap(
            String structName,
            Map<String, Object> hawkParamMap,
            Map<String, StructureDefnScript> structureDefnMap) throws Exception {


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
            localVarValue.setValue(new StringDataType(paramValue.toString()));
            localVarDeclScript.setLocalVarValue(localVarValue);
            localVarDeclScript.setLocalVarValExp(paramValue.toString());
            localVarDeclScript.setOrigVariableValExp(localVarDeclScript.getLocalVarValExp());
            structureDefnScript.addScript(i++, localVarDeclScript);
        }

        structureDefnMap.put(structName, structureDefnScript);
        return true;
    }

    public static boolean parseStructuresMap(Map<String, Map<String, Object>> xmlStructMap, Map<String, StructureDefnScript> structureDefnMap) throws Exception {

        for (Entry<String, Map<String, Object>> entry : xmlStructMap.entrySet()) {

            parseStructureMap(entry.getKey(), entry.getValue(), structureDefnMap);

        }

        return true;
    }

    public static boolean parseStructures(Map<Integer, String> scriptMap, Map<String, StructureDefnScript> structureDefnMap) throws Exception {

        parseStructureContainers(scriptMap, structureDefnMap);
        for (Entry<String, StructureDefnScript> entry : structureDefnMap.entrySet()) {
            StructureDefnScript structureDefnScript = entry.getValue();
            if (structureDefnScript.isXmlStruct()) {
                continue;
            }
            MultiLineContainer structureDefn = structureDefnScript.getMultiLineContainer();

            for (int j = structureDefn.getStart() + 1; j < structureDefn.getEnd(); j++) {

                SingleLineScript singleLineScript = null;

                Map<Integer, String> lineLocalVarMatcherMap = PatternMatcher.match(new LocalVarDeclScript().getPattern(), scriptMap.get(j));

                if (lineLocalVarMatcherMap == null) {

                    Map<Integer, String> lineStructMatcherMap = PatternMatcher.match(new StructureScript().getPatterns(), scriptMap.get(j));

                    if (lineStructMatcherMap != null) {

                        singleLineScript = (StructureScript)new StructureScript().createScript(lineStructMatcherMap);

                        structureDefnScript.addScript(j, singleLineScript);

                        if (singleLineScript != null) {

                            singleLineScript.setOuterMultiLineScript(structureDefnScript);

                        }

                    } else {
                        Map<Integer, String> lineArrayDeclMatcherMap = PatternMatcher.match(new ArrayDeclScript().getPatterns(), scriptMap.get(j));

                        if (lineArrayDeclMatcherMap != null) {

                            singleLineScript = (ArrayDeclScript) new ArrayDeclScript().createScript(lineArrayDeclMatcherMap);

                            structureDefnScript.addScript(j, singleLineScript);

                            if (singleLineScript != null) {

                                singleLineScript.setOuterMultiLineScript(structureDefnScript);

                            }

                        }
                    }

                } else {

                    singleLineScript = (LocalVarDeclScript)new LocalVarDeclScript().createScript(lineLocalVarMatcherMap);
                    if (singleLineScript != null) {
                        singleLineScript.execute();
                        structureDefnScript.addScript(j, singleLineScript);
                        singleLineScript.setOuterMultiLineScript(structureDefnScript);

                    }
                }
            }
        }
        return true;
    }

    private static MultiLineContainer extractStructureDefinition(Map<Integer, String> scriptMap, int x, String structureName) throws Exception {

        MultiLineContainer mlc = new MultiLineContainer();

        boolean firstStartBracktFound = false;

        Stack stack = new Stack();

        int start = x;

        int end = -1;

        int i = x;

        for (; i < scriptMap.size(); i++) {

            Map<Integer, String> startingBracketMap = PatternMatcher.match(
                    STRUCTURE_DEFN_BRACKET_START_PATTERN,
                    scriptMap.get(i));

            if (startingBracketMap != null) {

                if (!firstStartBracktFound) {

                    firstStartBracktFound = true;

                    start = i;
                }

                stack.push("{");

            }

            Map<Integer, String> endingBracketMap = PatternMatcher.match(
                    STRUCTURE_DEFN_BRACKET_END_PATTERN,
                    scriptMap.get(i));

            if (endingBracketMap != null) {

                stack.pop();

            }

            if (stack.isEmpty()) {
                break;
            }


        }

        if (!stack.isEmpty() || !firstStartBracktFound) {

            throw new Exception("Invalid structure {" + structureName + "}");
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

                status = localVarDeclScript.getLocalVar().getName().equals(structMember);

            } else if (entry.getValue() instanceof StructureScript) {

                StructureScript structureScript = (StructureScript) entry.getValue();

                status = structureScript.getStructVarName().getName().equals(structMember);

            }

        }

        return status;
    }

    
}
