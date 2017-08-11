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
package org.hawk.module.task;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import static org.hawk.constant.HawkConstant.HAWK_EXECUTION_PARAMS_DELIMETER;
import org.hawk.data.perf.PerfDataProcessor;
import org.common.di.AppContainer;

import org.hawk.executor.ModuleExecutor;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.runtime.java.object.JavaObjectHawkScriptConverter;
import org.hawk.logger.HawkLogger;
import org.hawk.module.IModule;
import org.commons.string.StringUtil;

/**
 * This contains tasks under a particular module.
 *
 * @version 1.0 7 Apr, 2010
 * @author msahu
 * @see ModuleExecutor
 * @see ScriptInterpreter
 */
public class SubTaskContainer implements Comparable<SubTaskContainer> {

    private static final HawkLogger logger = HawkLogger.getLogger(SubTaskContainer.class.getName());
    /**
     * Module of the subtask
     */
    IModule module;
    /**
     * Sub task name
     */
    String taskName;
    /**
     * Sub task's sequence
     */
    int sequence;
    /**
     * Sub task's <tt>Method</tt> object
     */
    Method taskMethod;
    /**
     * Sub task's iteration
     */
    int taskIteration;
    /**
     * If a exception occurs during sub task's execution, whether to ignore this
     * or not.
     */
    boolean ignoreException = false;
    /**
     * This is mainly used for display purpose... training mode.
     */
    String hawkParam = "";
    /**
     * This is used as method args when run in prop mode.
     */
    String hawkExecutionParams = "";
    /**
     * params for subtasks
     *
     */
    List<Object> params = new ArrayList<>();

    public Object[] getParams() {

        Object[] args = new Object[this.params.size()];
        int i = 0;
        for (Object param : this.params) {
            args[i] = param;
            i++;
        }
        Object[] rtn = new Object[1];
        rtn[0] = args;
        return rtn;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public boolean addParam(Object param) {
        return this.params.add(param);
    }

    public boolean clearParams() {
        this.params.clear();
        return true;
    }

    public boolean parseModuleParameters(Map<Integer, IObjectScript> paramMap) throws Exception {

        this.clearParams();

        for (Map.Entry<Integer, IObjectScript> entry : paramMap.entrySet()) {

            IObjectScript paramScript = entry.getValue();

            Object paramObj = paramScript.toJava();

            this.addParam(paramObj);

        }

        return true;
    }

    public boolean isIgnoreException() {
        return ignoreException;
    }

    public void setIgnoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
    }

    public IModule getModule() {
        return module;
    }

    public void setModule(IModule module) {
        this.module = module;
    }

    /**
     * Besides from executing subtask it collects it's execution time which in
     * turn helps plotting the graph.
     *
     * @return
     * @throws org.hawk.exception.Exception
     * @see HawkPerfDataCollector
     */
    public IObjectScript execute() throws Exception {
        IObjectScript status = null;
        PerfDataProcessor hawkPerfDataCollector = AppContainer.getInstance().getBean(PerfDataProcessor.class);
        
        Object result;
        try {
            hawkPerfDataCollector.start(this.getModule(), this.getTaskName());
            result = this.taskMethod.invoke(this.module, this.getParams());
            hawkPerfDataCollector.end(this.getModule(), this.getTaskName());
            status = JavaObjectHawkScriptConverter.createHawkScript(result);
        } catch (Throwable th) {
            th.printStackTrace();;
            hawkPerfDataCollector.endWithFailure(this.getModule(), this.getTaskName());

            if (!this.isIgnoreException()) {
                logger.error("error while executing the task {" + this.getTaskSignature() + "} ..." + th.getMessage());
                System.out.println("error while executing the task {" + this.getTaskSignature() + "} ..." + th.getMessage());
                throw new Exception(th);
            } else {
                throw new Exception(th);
            }

        }
        return status;
    }

   

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskSignature() {
        return this.getTaskName() + "(" + this.getHawkParam() + ")";
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getHawkParam() {
        return hawkParam;
    }

    public void setHawkParam(String hawkParam) {
        this.hawkParam = hawkParam;
    }

    public String getHawkExecutionParams() {
        return hawkExecutionParams;
    }

    public void setHawkExecutionParams(String hawkExecutionParams) {
        this.hawkExecutionParams = hawkExecutionParams;
        if (!StringUtil.isNullOrEmpty(this.hawkExecutionParams)) {
            List<Object> hawkExecutionParamsList = new ArrayList<>();
            StringTokenizer strTok = new StringTokenizer(this.hawkExecutionParams, HAWK_EXECUTION_PARAMS_DELIMETER);
            while (strTok.hasMoreElements()) {
                hawkExecutionParamsList.add(strTok.nextElement());
            }
            this.setParams(hawkExecutionParamsList);
        }
    }

    public Method getTaskMethod() {
        return taskMethod;
    }

    public void setTaskMethod(Method taskMethod) {
        this.taskMethod = taskMethod;
    }

    public int getTaskIteration() {
        return taskIteration;
    }

    public void setTaskIteration(int taskIteration) {
        this.taskIteration = taskIteration;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.module != null ? this.module.hashCode() : 0);
        hash = 97 * hash + (this.taskName != null ? this.taskName.hashCode() : 0);
        hash = 97 * hash + this.sequence;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SubTaskContainer other = (SubTaskContainer) obj;
        if (this.module != other.module && (this.module == null || !this.module.equals(other.module))) {
            return false;
        }
        if ((this.taskName == null) ? (other.taskName != null) : !this.taskName.equals(other.taskName)) {
            return false;
        }
        if (this.sequence != other.sequence) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(SubTaskContainer otherTaskContainer) {
        int result;
        if (otherTaskContainer == null) {
            result = -1;
        } else {
            result = this.getSequence() - otherTaskContainer.getSequence();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SubTaskContainer{");
        sb.append("module=");
        sb.append(module);
        sb.append("taskName=");
        sb.append(taskName);
        sb.append("params=");
        sb.append(params);
        sb.append("}");
        return sb.toString();
    }
}
