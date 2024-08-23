/*
 * This file is part of j-hawk
 *  
 *
 * 
 *
 *  
 *  
 *  
 *  
 *
 * 
 * 
 *
 * 
 */
package org.hawk.lang.multiline;

import org.hawk.lang.comment.MultiLineCommentScript;
import org.hawk.lang.condition.IfScript;
import org.hawk.lang.function.FunctionScript;
import org.hawk.lang.loop.DoWhileLoopScript;
import org.hawk.lang.loop.ForLoopScript;
import org.hawk.lang.loop.WhileLoopScript;
import org.hawk.sequence.DefaultSequenceProvider;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class MultiLineScriptVerticalSequenceProvider extends DefaultSequenceProvider {

    public MultiLineScriptVerticalSequenceProvider() {
        this.getSequenceMap().put(ForLoopScript.class, 1000);
        this.getSequenceMap().put(WhileLoopScript.class, 2000);
        this.getSequenceMap().put(DoWhileLoopScript.class, 3000);
        this.getSequenceMap().put(IfScript.class, 4000);
        this.getSequenceMap().put(MultiLineCommentScript.class, 5000);
        this.getSequenceMap().put(FunctionScript.class, 6000);
    }
}
