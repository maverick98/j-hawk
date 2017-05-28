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
package org.hawk.executor.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.commons.implementor.InstanceVisitable;
import org.hawk.logger.HawkLogger;
import org.hawk.pattern.PatternMatcher;
import org.commons.string.StringUtil;

/**
 *
 * @author Manoranjan Sahu
 */
   
   
public class HawkExecutionCommandFactory implements IHawkExecutionCommandFactory {

    private static final HawkLogger logger = HawkLogger.getLogger(HawkExecutionCommandFactory.class.getName());
    private static List<IHawkExecutionCommand> cachedHawkExecutionCommands = new ArrayList<IHawkExecutionCommand>();

    @Override
    public List<IHawkExecutionCommand> getHawkExecutionCommands() throws Exception {

        if (cachedHawkExecutionCommands != null && !cachedHawkExecutionCommands.isEmpty()) {
            return cachedHawkExecutionCommands;

        }

        InstanceVisitable instanceVisitable = new InstanceVisitable();
        instanceVisitable.setClazz(HawkExecutionCommand.class);

        try {
            new HawkExecutionCommandVisitorImpl().visit(instanceVisitable);
        } catch (Exception ex) {
            throw new Exception(ex);
        }

        return cachedHawkExecutionCommands;
    }

    private static class HawkExecutionCommandVisitorImpl extends HawkExecutionCommandVisitor {

        @Override
        public void onVisit(HawkExecutionCommand instance) {
            cachedHawkExecutionCommands.add(instance);

        }
    }

    @Override
    public IHawkExecutionCommand create(String args[]) throws Exception {
        IHawkExecutionCommand result = null;
        List<IHawkExecutionCommand> hawkExecutionCommands = getHawkExecutionCommands();
        String command = StringUtil.stringifyArgs(args);
        for (IHawkExecutionCommand hawkExecutionCommand : hawkExecutionCommands) {
            
            if (hawkExecutionCommand.getRegEx() != null) {

                Pattern pattern = Pattern.compile(hawkExecutionCommand.getRegEx());
                Map<Integer, String> map = PatternMatcher.match(pattern, command);
                if (map != null && !map.isEmpty()) {
                    result = hawkExecutionCommand;
                    result.onCommandFound(map);
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public String getCommandUsage() {
        StringBuilder result = new StringBuilder();
        String prefix = "                     -";
        List<IHawkExecutionCommand> hawkExecutionCommands = null;
        try {
            hawkExecutionCommands = getHawkExecutionCommands();
        } catch (Exception ex) {
            logger.error(ex);
        }
        if (hawkExecutionCommands != null) {
            for (IHawkExecutionCommand hawkExecutionCommand : hawkExecutionCommands) {
                result.append(prefix);
                result.append(hawkExecutionCommand.getCommandUsage());
                result.append("\n");
            }
        }
        return result.toString();
    }
}
