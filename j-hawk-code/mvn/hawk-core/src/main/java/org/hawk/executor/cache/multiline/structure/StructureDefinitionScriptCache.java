/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
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
package org.hawk.executor.cache.multiline.structure;

import java.util.HashMap;
import java.util.Map;
import org.common.di.AppContainer;

import org.hawk.executor.cache.multiline.MultiLineScriptCache;
import org.hawk.lang.multiline.MultiLineContainer;
import org.hawk.lang.object.StructureDefnScript;
import org.hawk.lang.object.XMLStructIncludeScript;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @author Manoranjan Sahu
 */
@ScanMe(true)
   
   
public class StructureDefinitionScriptCache extends MultiLineScriptCache implements IStructureDefinitionScriptCache {

    /**
     * A map containing key as function name and value as
     * <tt>MultiLineContainer</tt>
     *
     * @see MultiLineContainer
     */
    private Map<String, StructureDefnScript> structureDefnMap = new HashMap<>();

    
    @Override
    public boolean cache() throws Exception {

        Map<String, Map<String, Object>> xmlStructMap = XMLStructIncludeScript.getInstance().getXmlStructMap();

        StructureDefnScript.parseStructuresMap(xmlStructMap, this.structureDefnMap);

        StructureDefnScript.parseStructures(this.getHawkInput().getScriptMap(), this.structureDefnMap);

        return true;
    }

    @Override
    public boolean isInsideMultiLineScript(int i) {
        boolean status = false;

        if (this.structureDefnMap != null) {

            for (Map.Entry<String, StructureDefnScript> entry : this.structureDefnMap.entrySet()) {

                status = entry.getValue().isInside(i, true);

                if (status) {

                    break;

                }

            }
        }


        return status;
    }

    

    /**
     * This checks if structName is defined in the hawk script
     *
     * @param structName
     * @return true if structName is defined otherwise false
     * @throws org.hawk.exception.Exception
     */
    @Override
    public boolean doesStructExist(String structName) throws Exception {

        if (structName == null || structName.isEmpty()) {

            throw new Exception("Invalid struct name");

        }

        return this.getStructureDefnMap().containsKey(structName);
    }

    /**
     * This checks if a structName and it's member structMember are defined in
     * the hawk script
     *
     * @param structName
     * @param structMember
     * @return
     * @throws org.hawk.exception.Exception
     */
    @Override
    public boolean doesStructMemberExist(String structName, String structMember) throws Exception {

        if (structName == null || structName.isEmpty()) {

            throw new Exception("Invalid struct name");

        }

        if (structMember == null || structMember.isEmpty()) {

            throw new Exception("Invalid struct member");

        }

        boolean status;

        if (this.doesStructExist(structName)) {

            StructureDefnScript structDefnScript = this.getStructureDefnMap().get(structName);

            status = structDefnScript.doesMemberExist(structMember);

        } else {

            status = false;

        }

        return status;
    }

    @Override
    public  StructureDefnScript findStructureDefn(String token){
        
        return this.getStructureDefnMap().get(token);
    }
    
    public Map<String, StructureDefnScript> getStructureDefnMap() {
        return structureDefnMap;
    }

    public void setStructureDefnMap(Map<String, StructureDefnScript> structureDefnMap) {
        this.structureDefnMap = structureDefnMap;
    }

    @Override
    public boolean reset() {
        structureDefnMap = new HashMap<>();
        return true;
    }

    @Override
    @Create
    public MultiLineScriptCache create() {
        return AppContainer.getInstance().getBean(StructureDefinitionScriptCache.class);
    }
}
