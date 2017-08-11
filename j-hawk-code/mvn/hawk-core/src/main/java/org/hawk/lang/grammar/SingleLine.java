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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.grammar;

import org.hawk.lang.comment.SingleLineCommentScript.SingleLineComment;
import org.hawk.lang.console.EchoNScript.EchoN;
import org.hawk.lang.console.EchoScript.Echo;
import org.hawk.lang.console.ShowScript.Show;
import org.hawk.lang.file.ReadLineScript.ReadLine;
import org.hawk.lang.function.ExecFunctionScript.ExecFunction;
import org.hawk.lang.function.ExecModuleScript.ExecModule;
import org.hawk.lang.function.ReturnScript.Return;
import org.hawk.lang.loop.BreakScript.Break;
import org.hawk.lang.object.ArrayDeclScript.ArrayDecl;
import org.hawk.lang.object.AssignmentScript.Assignment;
import org.hawk.lang.object.matrix.MatrixScript.Matrix;
import org.hawk.lang.object.StructureScript.Structure;
import org.hawk.lang.object.VariableDeclScript.VariableDecl;
import org.hawk.lang.object.VARXVariableDeclProxyScript.VarxVariableDecl;
import org.hawk.lang.thread.ExecBackgroundSingleLineScript.ExecBackground;
import org.hawk.lang.thread.ExecParallelSingleLineScript.ExecParallel;
import org.hawk.lang.util.ThinkScript.Think;

/**
 *
 * @author manosahu
 */
public class SingleLine {

    private Show show;

    private EchoN echoN;

    private Echo echo;

    private Think think;

    private ExecFunction execFunction;

    private ExecModule execModule;

    private ArrayDecl arrayDecl;
    
    private Matrix matrix;

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    private Break brek;

    private Return retturn;

    private Structure structure;

    private ExecParallel execParallel;

    private ExecBackground execBackground;
    
    private ReadLine readLine;
    
    private VariableDecl variableDecl;

    public VariableDecl getVariableDecl() {
        return variableDecl;
    }

    public void setVariableDecl(VariableDecl variableDecl) {
        this.variableDecl = variableDecl;
    }
    
    private SingleLineComment singleLineComment;

    private VarxVariableDecl varxVariableDecl;
    
    private Assignment assignment;

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public VarxVariableDecl getVarxVariableDecl() {
        return varxVariableDecl;
    }

    public void setVarxVariableDecl(VarxVariableDecl varxVariableDecl) {
        this.varxVariableDecl = varxVariableDecl;
    }

   

   
   
    public SingleLineComment getSingleLineComment() {
        return singleLineComment;
    }

    public void setSingleLineComment(SingleLineComment singleLineComment) {
        this.singleLineComment = singleLineComment;
    }

   

    public ReadLine getReadLine() {
        return readLine;
    }

    public void setReadLine(ReadLine readLine) {
        this.readLine = readLine;
    }

    public ExecParallel getExecParallel() {
        return execParallel;
    }

    public void setExecParallel(ExecParallel execParallel) {
        this.execParallel = execParallel;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Return getRetturn() {
        return retturn;
    }

    public void setRetturn(Return retturn) {
        this.retturn = retturn;
    }

    public Break getBrek() {
        return brek;
    }

    public void setBrek(Break brek) {
        this.brek = brek;
    }

    public ArrayDecl getArrayDecl() {
        return arrayDecl;
    }

    public void setArrayDecl(ArrayDecl arrayDecl) {
        this.arrayDecl = arrayDecl;
    }

    public ExecModule getExecModule() {
        return execModule;
    }

    public void setExecModule(ExecModule execModule) {
        this.execModule = execModule;
    }

    public ExecFunction getExecFunction() {
        return execFunction;
    }

    public void setExecFunction(ExecFunction execFunction) {
        this.execFunction = execFunction;
    }

    public Think getThink() {
        return think;
    }

    public void setThink(Think think) {
        this.think = think;
    }

    public Echo getEcho() {
        return echo;
    }

    public void setEcho(Echo echo) {
        this.echo = echo;
    }

    public EchoN getEchoN() {
        return echoN;
    }

    public void setEchoN(EchoN echoN) {
        this.echoN = echoN;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public ExecBackground getExecBackground() {
        return execBackground;
    }

    public void setExecBackground(ExecBackground execBackground) {
        this.execBackground = execBackground;
    }
}
