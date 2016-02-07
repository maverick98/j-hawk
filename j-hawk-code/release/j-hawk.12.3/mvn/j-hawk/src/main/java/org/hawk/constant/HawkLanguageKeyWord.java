
package org.hawk.constant;

import org.hawk.module.script.AliasScript;
import org.hawk.module.script.ArrayDeclScript;
import org.hawk.module.script.BreakScript;
import org.hawk.module.script.DoWhileLoopScript;
import org.hawk.module.script.EchoScript;
import org.hawk.module.script.ElseIfScript;
import org.hawk.module.script.ExecBackgroundSingleLineScript;
import org.hawk.module.script.ExecFunctionScript;
import org.hawk.module.script.ExecModuleScript;
import org.hawk.module.script.ExecParallelSingleLineScript;
import org.hawk.module.script.ForLoopScript;
import org.hawk.module.script.IfScript;
import org.hawk.module.script.ReadLineScript;
import org.hawk.module.script.ReturnScript;
import org.hawk.module.script.StructureDefnScript;
import org.hawk.module.script.StructureScript;
import org.hawk.module.script.ThinkScript;
import org.hawk.module.script.WhileLoopScript;
import org.hawk.module.script.type.Variable;

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
     * @see ReturnScript
     */
    String returnStatement="return";

    /**
     * @see EchoScript
     * 
     */
    String echo="echo";
    
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
