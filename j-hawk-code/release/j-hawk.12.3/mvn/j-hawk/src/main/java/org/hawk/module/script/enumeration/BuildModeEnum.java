

package org.hawk.module.script.enumeration;

import org.hawk.module.script.ScriptUsage;

/**
 * <p>This defines various way a hawk script can be executed.
 *
 * <p>DEBUG :- runs in debug mode
 * Usage :- sh run-perf-hawk.sh -debug -f {hawk script file}
 *
 * <p>TRAINING :- shows the tasks implemented by the developer
 * Usage :- sh run-perf-hawk.sh -training
 *
 * <p>PERF :- This runs in perf mode
 * Usage :- sh run-perf-hawk.sh -perf -f {hawk script file}
 *
 * <p>HELP :- shows help message
 * Usage :- sh run-perf-hawk.sh -help
 *
 * <p>COMPILE :- This parses the hawk script for syntax check
 * Usage :- sh run-perf-hawk.sh -compile -f {hawk script file}
 *
 *
 * @version 1.0 9 Apr, 2010
 * @author msahu
 * @see ScriptUsage
 */
   public  enum BuildModeEnum{
        DEBUG,
        TRAINING,
        PERF,
        HELP,
        COMPILE,
        PLOTTING,
        SCRIPTING,
        VERSION


    }




