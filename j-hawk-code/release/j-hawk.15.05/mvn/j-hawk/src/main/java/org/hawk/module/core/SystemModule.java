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
package org.hawk.module.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.commons.process.ProcessUtil;
import org.common.di.AppContainer;
import org.hawk.lang.function.AbstractFunctionStackUtility;
import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.IObjectScript;
import org.hawk.module.annotation.SubTask;
 import org.commons.reflection.Create;
import org.common.di.ScanMe;

/**
 *
 * @version 1.0 10 Jul, 2010
 * @author msahu
 */
@ScanMe(true)
//@Component(SYSTEMMODULE)
//@Qualifier(DEFAULTQUALIFIER)

public class SystemModule extends HawkCoreModule {

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return "SystemModule";
    }

    @Override
    public boolean startUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean reset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SubTask(name = "execute", sequence = 1, ignoreException = false, hawkParam = "var command")
    public String execute(Object... args) throws Exception {

        return ProcessUtil.executeProcess((String) args[0]) + "";

    }

    @SubTask(name = "dumpStack", sequence = 1, ignoreException = false, hawkParam = "")
    public String dumpFunctionStack(Object... args) throws Exception {

        String stackDump = AbstractFunctionStackUtility.getFunctionStack().get().dump();
        System.out.println(stackDump);
        return stackDump;

    }

    @SubTask(name = "currentTime", sequence = 1, ignoreException = false, hawkParam = "")
    public long currentTime(Object... args) throws Exception {

        return System.currentTimeMillis();

    }

    @SubTask(name = "length", sequence = 1, ignoreException = false, hawkParam = "var array[] | arg")
    public int length(Object... args) throws Exception {

        IObjectScript script = (IObjectScript) args[0];
        return script.length();

    }

    private static class ScriptComparator implements Comparator<IObjectScript>, Serializable {

        private static final long serialVersionUID = 398825763181265L;

        @Override
        public int compare(IObjectScript myScript, IObjectScript herScript) {
            return myScript.toUI().compareTo(herScript.toUI());
        }
    }

    @SubTask(name = "sort", sequence = 1, ignoreException = false, hawkParam = "var array")
    public boolean sort(Object... args) throws Exception {

        ArrayDeclScript arrayDeclScript = (ArrayDeclScript) args[0];
        Map<Integer, IObjectScript> map = arrayDeclScript.getMembers();
        Collection<IObjectScript> memberColl = map.values();
        List<IObjectScript> members = new ArrayList<IObjectScript>();
        for (IObjectScript script : memberColl) {
            members.add(script);
        }
        Collections.sort(members, new ScriptComparator());
        for (int i = 0; i < map.size(); i++) {
            map.put(i + 1, members.get(i));
        }
        arrayDeclScript.setMembers(map);
        return true;
    }

    @SubTask(name = "charAt", sequence = 1, ignoreException = false, hawkParam = "var input, var index")
    public char charAt(Object... args) {
        String input = (String) args[0];
        Integer index = (Integer) args[1];
        return input.charAt(index);
    }

    @SubTask(name = "codePointAt", sequence = 1, ignoreException = false, hawkParam = "var input, var index")
    public int codePointAt(Object... args) {
        String input = (String) args[0];
        Integer index = (Integer) args[1];
        return input.codePointAt(index);
    }

    @SubTask(name = "codePointBefore", sequence = 1, ignoreException = false, hawkParam = "var input, var index")
    public int codePointBefore(Object... args) {
        String input = (String) args[0];
        Integer index = (Integer) args[1];
        return input.codePointBefore(index);
    }

    @SubTask(name = "codePointCount", sequence = 1, ignoreException = false, hawkParam = "var input, var beginIndex,var endIndex")
    public int codePointCount(Object... args) {
        String input = (String) args[0];
        Integer beginIndex = (Integer) args[1];
        Integer endIndex = (Integer) args[2];
        return input.codePointCount(beginIndex, endIndex);
    }

    @SubTask(name = "offsetByCodePoints", sequence = 1, ignoreException = false, hawkParam = "var input, var index,var codePointOffset")
    public int offsetByCodePoints(Object... args) {
        String input = (String) args[0];
        Integer beginIndex = (Integer) args[1];
        Integer endIndex = (Integer) args[2];
        return input.offsetByCodePoints(beginIndex, endIndex);
    }

    @SubTask(name = "getChars", sequence = 1, ignoreException = false, hawkParam = "var input, var srcBegin,var codePointOffset")
    public char[] getChars(Object... args) {

        String input = (String) args[0];
        Integer srcBegin = (Integer) args[1];
        Integer srcEnd = (Integer) args[2];
        Integer dstBegin = (Integer) args[3];
        char[] dst = new char[input.length()];
        input.getChars(srcBegin, srcEnd, dst, dstBegin);
        return dst;
    }

    @SubTask(name = "compareTo", sequence = 1, ignoreException = false, hawkParam = "var other")
    public int compareTo(Object... args) {
        String input = (String) args[0];
        String anotherString = (String) args[1];
        return input.compareTo(anotherString);
    }
    @SubTask(name = "compareToIgnoreCase", sequence = 1, ignoreException = false, hawkParam = "var other")
    public int compareToIgnoreCase(Object... args) {
        String input = (String) args[0];
        String anotherString = (String) args[1];
        return input.compareToIgnoreCase(anotherString);
    }
    @SubTask(name = "regionMatches", sequence = 1, ignoreException = false, hawkParam = "var input,var toffset, var other, var ooffset, var len")
    public boolean regionMatches(Object ... args) {
        String input = (String) args[0];
        Integer toffset = (Integer)args[1];
        String other = (String) args[1];
        Integer ooffset = (Integer)args[2];
        Integer len = (Integer)args[3];
        return input.regionMatches(toffset,other,ooffset,len);
    }
    

    @Create
    public HawkCoreModule create() {
        return AppContainer.getInstance().getBean(this.getClass());
    }
}
