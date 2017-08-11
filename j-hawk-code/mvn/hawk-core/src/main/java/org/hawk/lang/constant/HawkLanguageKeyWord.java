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

package org.hawk.lang.constant;

import org.hawk.lang.condition.ElseIfScript;
import org.hawk.lang.condition.IfScript;
import org.hawk.lang.console.EchoScript;
import org.hawk.lang.file.ReadLineScript;
import org.hawk.lang.function.ExecFunctionScript;
import org.hawk.lang.function.ExecModuleScript;
import org.hawk.lang.function.ReturnScript;
import org.hawk.lang.loop.BreakScript;
import org.hawk.lang.loop.DoWhileLoopScript;
import org.hawk.lang.loop.ForLoopScript;
import org.hawk.lang.loop.WhileLoopScript;
import org.hawk.lang.object.ArrayDeclScript;
import org.hawk.lang.object.StructureDefnScript;
import org.hawk.lang.object.StructureScript;
import org.hawk.lang.thread.ExecBackgroundSingleLineScript;
import org.hawk.lang.thread.ExecParallelSingleLineScript;
import org.hawk.lang.type.Variable;
import org.hawk.lang.util.AliasScript;
import org.hawk.lang.util.ThinkScript;

/**
 * Placeholder for all the keywords that are being used in hawk scripting language.
 * @author msahu
 */
public interface HawkLanguageKeyWord {

    /**
     * @see ExecParallelSingleLineScript
     */
    String wait="wait";

    /**
     * @see ExecParallelSingleLineScript
     */
    String reset="reset";
    
    /**
     * @see ExecParallelSingleLineScript
     * @see ExecFunctionScript
     * 
     */
    String function="function";
    
    /**
     * @see ExecParallelSingleLineScript
     * @see ExecFunctionScript
     * @see ExecModuleScript
     * 
     */
    String exec="exec";

    /**
     * @see ExecParallelSingleLineScript
     * @see ExecFunctionScript
     *
     */
    String execParallel="exec-parallel";

    /**
     * @see ExecBackgroundSingleLineScript
     * @see ExecFunctionScript
     *
     */
    String execBackground="exec-bg";
    /**
     * @see ForLoopScript
     */
    String forLoop="for";

    /**
     * @see IfScript
     */
    String ifCondition="if";

    /**
     * @see ElseIfScript
     */
    String elseCondition="else";

    /**
     * @see StructureDefnScript
     * @see StructureScript
     */
    String struct="struct";

    /**
     * @see Variable
     */
    String var="var";
    
    /**
     * @see Variable
     */
    String varx="varx";
    

    /**
     * @see ReturnScript
     */
    String returnStatement="return";

    /**
     * @see EchoScript
     * 
     */
    String echo="echo";
    
     /**
     * @see EchoScript
     * 
     */
    String echon="echon";
    
     /**
     * @see EchoScript
     * 
     */
    String show=">";
    
    
    /**
     * @see AliasScript
     */
    String alias="alias";

    /**
     * @see BreakScript
     */
    String breakStatement="break";

    /**
     * @see ThinkScript
     */
    String think="think";

    /**
     * @see ExecBackgroundSingleLineScript
     * @see ExecParallelSingleLineScript
     */
    String currentThreadName="curThreadName";

    /**
     * @see ReadLineScript
     */
    String readLine="read-line";

    /**
     * @see ArrayDeclScript
     * 
     */
    String newKeyWord="new";
    
    /**
     * @see WhileLoopScript
     */
    String whileLoop="while";

    /**
     * @see DoWhileLoopScript
     */
    String doWhileLoop="do";

}
