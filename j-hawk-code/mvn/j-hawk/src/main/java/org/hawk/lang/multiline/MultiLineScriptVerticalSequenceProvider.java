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
//@Component(MULTILINESCRIPTVERTICALSEQUENCEPROVIDER)
//@Qualifier(DEFAULTQUALIFIER)
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
