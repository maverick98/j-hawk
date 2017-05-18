/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.monitor;

/**
 *
 * @author manosahu
 */
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sampler {
  private static final ThreadMXBean TMX=ManagementFactory.getThreadMXBean();
  private static String CLASS, METHOD;
  private static CallTree ROOT;
  private static ScheduledExecutorService EXECUTOR;

  public static synchronized void startSampling(String className, String method) {
    if(EXECUTOR!=null) throw new IllegalStateException("sampling in progress");
    System.out.println("sampling started");
    CLASS=className;
    METHOD=method;
    EXECUTOR = Executors.newScheduledThreadPool(1);
    // "fixed delay" reduces overhead, "fixed rate" raises precision
    EXECUTOR.scheduleWithFixedDelay(new Runnable() {
      public void run() {
        newSample();
      }
    }, 150, 75, TimeUnit.MILLISECONDS);
  }
  public static synchronized CallTree stopSampling() throws InterruptedException {
    if(EXECUTOR==null) throw new IllegalStateException("no sampling in progress");
    EXECUTOR.shutdown();
    EXECUTOR.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    EXECUTOR=null;
    final CallTree root = ROOT;
    ROOT=null;
    return root;
  }
  public static void printCallTree(CallTree t) {
    if(t==null) System.out.println("method not seen");
    else printCallTree(t, 0, 100);
  }
  private static void printCallTree(CallTree t, int ind, long percent) {
    long num=0;
    for(CallTree ch:t.values()) num+=ch.count;
    if(num==0) return;
    for(Map.Entry<List<String>,CallTree> ch:t.entrySet()) {
      CallTree cht=ch.getValue();
      StringBuilder sb = new StringBuilder();
      for(int p=0; p<ind; p++) sb.append(' ');
      final long chPercent = cht.count*percent/num;
      sb.append(chPercent).append("% (").append(cht.cpu*percent/num)
        .append("% cpu) ").append(ch.getKey()).append(" ");
      System.out.println(sb.toString());
      printCallTree(cht, ind+2, chPercent);
    }
  }
  static class CallTree extends HashMap<List<String>, CallTree> {
    long count=1, cpu;
    CallTree(boolean cpu) { if(cpu) this.cpu++; }
    CallTree getOrAdd(String cl, String m, boolean cpu) {
      List<String> key=Arrays.asList(cl, m);
      CallTree t=get(key);
      if(t!=null) { t.count++; if(cpu) t.cpu++; }
      else put(key, t=new CallTree(cpu));
      return t;
    }
  }
  static void newSample() {
    for(ThreadInfo ti:TMX.dumpAllThreads(false, false)) {
      final boolean cpu = ti.getThreadState()==State.RUNNABLE;
      StackTraceElement[] stack=ti.getStackTrace();
      for(int ix = stack.length-1; ix>=0; ix--) {
        StackTraceElement ste = stack[ix];
        if(!ste.getClassName().equals(CLASS)||!ste.getMethodName().equals(METHOD))
          continue;
        CallTree t=ROOT;
        if(t==null) ROOT=t=new CallTree(cpu);
        for(ix--; ix>=0; ix--) {
          ste = stack[ix];
          t=t.getOrAdd(ste.getClassName(), ste.getMethodName(), cpu);
        }
      }
    }
  }
}
