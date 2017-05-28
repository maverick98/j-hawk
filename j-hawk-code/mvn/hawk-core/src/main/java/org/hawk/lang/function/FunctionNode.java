/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hawk.lang.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.commons.implementor.IVisitable;
import org.commons.implementor.IVisitor;
import org.hawk.lang.object.IObjectScript;
import org.hawk.lang.type.Variable;

/**
 *
 * @author manosahu
 */
public class FunctionNode implements IVisitable {

    private static enum NodeType {

        ROOT, FUNCTION, PARAMETER, UNKNOWN;
    }
    private static final String ROOT_KEY = "ROOT";
    private String key;
    private NodeType nodeType = NodeType.UNKNOWN;

    private FunctionNode parent = null;

    private Map<String, FunctionNode> children = new HashMap<>();

    private FunctionScript value = null;

    public FunctionNode() {

    }

    public FunctionNode(String key) {
        this.key = key;
    }

    public FunctionScript getValue() {
        return value;
    }

    public void setValue(FunctionScript value) {
        this.value = value;
    }

    public boolean containsFunctionScript() {
        return this.getValue() != null;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public boolean containsFunctionName() {
        return this.nodeType == NodeType.FUNCTION;
    }

    public boolean containsParamType() {
        return this.nodeType == NodeType.PARAMETER;
    }

    public boolean isRoot() {
        return this.parent == null && this.getKey().equals(ROOT_KEY) && this.nodeType == NodeType.ROOT;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    public boolean hasChildren() {
        return !this.isLeaf();
    }

    public boolean containsChild(FunctionNode childFunctionNode) {
        if (childFunctionNode == null) {
            return false;
        }

        return this.getChildren().containsKey(childFunctionNode.getKey());

    }

    public FunctionNode addChild(FunctionNode childFunctionNode) {
        if (childFunctionNode == null) {
            return null;
        }
        FunctionNode rtnNode;
        if (!this.containsChild(childFunctionNode)) {
            childFunctionNode.setParent(this);
            this.getChildren().put(childFunctionNode.getKey(), childFunctionNode);
            rtnNode = childFunctionNode;
        } else {
            rtnNode = this.getChildren().get(childFunctionNode.getKey());
        }
        return rtnNode;
    }

    public static FunctionNode createRootFunctionNode() {

        FunctionNode rootFunctionNode = new FunctionNode();
        rootFunctionNode.setParent(null);
        rootFunctionNode.setNodeType(NodeType.ROOT);
        rootFunctionNode.setKey(ROOT_KEY);
        return rootFunctionNode;
    }

    public FunctionNode getParent() {
        return parent;
    }

    public void setParent(FunctionNode parent) {
        this.parent = parent;
    }

    public Integer findNextIndex() {
        return this.children.size();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, FunctionNode> getChildren() {
        return children;
    }

    public void setChildren(Map<String, FunctionNode> children) {
        this.children = children;
    }

    public boolean isKeyPresent() {
        return this.getKey() != null && !this.getKey().trim().isEmpty();
    }

    @Override
    public void accept(IVisitor functionNodeVisitor) {
        functionNodeVisitor.visit(this);
    }

    private FunctionNode findFunctionNode(FunctionNode functionNode, String key) {
        FunctionNode rtnFunctionNode;
        if (functionNode == null || !functionNode.isRoot()) {
            return null;
        }
        if (functionNode.getKey().equals(key)) {
            return functionNode;
        }
        rtnFunctionNode = functionNode.getChildren().get(key);

        return rtnFunctionNode;
    }
    
    public FunctionScript findAnyFunctionScript(final String functionName) throws Exception {
        if (functionName == null || functionName.isEmpty()) {

            throw new Exception("invalid function name");

        }

        class FunctionScriptContainer {

            private FunctionScript functionScript;

            public FunctionScript getFunctionScript() {
                return functionScript;
            }

            public void setFunctionScript(FunctionScript functionScript) {
                this.functionScript = functionScript;
            }

            public FunctionScriptContainer() {

            }

            public FunctionScriptContainer(FunctionScript functionScript) {
                this.functionScript = functionScript;
            }
        }
        final FunctionScriptContainer functionScriptContainer = new FunctionScriptContainer();
        this.visitFunctionNode
                (
                        this,
                        new AbstractFunctionNodeVisitor() {

                            @Override
                            public boolean onVisit(FunctionNode functionNode) {
                                boolean visited;
                                FunctionScript currentFunctionScript = functionNode.getValue();
                                if (currentFunctionScript.getFunctionName().equals(functionName)) {
                                    functionScriptContainer.setFunctionScript(currentFunctionScript);
                                    visited = true;
                                } else {
                                    visited = false;
                                }
                                return visited;
                            }
                        }
                );

        return functionScriptContainer.getFunctionScript();

    }
    
    public List<FunctionScript> findAllOverloadedFunctionScripts(final String functionName) throws Exception {
        if (functionName == null || functionName.isEmpty()) {

            throw new Exception("invalid function name");

        }
        final List<FunctionScript> result = new ArrayList<>();

        this.visitFunctionNode
                (
                        this,
                        new AbstractFunctionNodeVisitor() {

                            @Override
                            public boolean onVisit(FunctionNode functionNode) {
                                boolean shouldIControlVisit = false;
                                FunctionScript currentFunctionScript = functionNode.getValue();
                                if (currentFunctionScript.getFunctionName().equals(functionName)) {
                                    result.add(currentFunctionScript);

                                }
                                return shouldIControlVisit;
                            }
                        }
                );
        return result;

    }

    public FunctionScript findFunctionScript(String functionName, Map<Integer, IObjectScript> paramMap) {

        FunctionScript functionScript;

        FunctionNode functionNode = this.findFunctionNode(this, functionName);
        if (functionNode == null) {
            return null;
        }
        if (paramMap == null || paramMap.isEmpty()) {
            functionScript = functionNode.getValue();
        } else {
            FunctionNode paramNode = functionNode;
            for (Entry<Integer, IObjectScript> entry : paramMap.entrySet()) {
                IObjectScript param = entry.getValue();

                String mangledParam = param.mangle();
                if (!paramNode.isLeaf()) {
                    FunctionNode fn = paramNode.getChildren().get(mangledParam);
                    if (fn == null) {
                        return null;
                    } else {
                        paramNode = fn;
                    }
                }

            }
            functionScript = paramNode.getValue();

        }

        return functionScript;
    }

    public boolean isInsideFunctionScript(FunctionNode node, int i) {
        boolean status = false;
        if (node.containsFunctionScript()) {
            status = node.getValue().isInside(i, true);
            if (status) {
                return status;
            }
        }
        for (Entry<String, FunctionNode> entry : node.getChildren().entrySet()) {

            FunctionNode nextNode = entry.getValue();

            status = this.isInsideFunctionScript(nextNode, i);
            if (status) {
                return status;
            }
        }

        return status;
    }

    public boolean visitFunctionNode(FunctionNode node, IFunctionNodeVisitor functionNodeVisitor) {

        boolean visited;

        if (node.containsFunctionScript()) {
            visited = functionNodeVisitor.onVisit(node);
            if (visited) {
                return visited;
            }

        }
        for (Entry<String, FunctionNode> entry : node.getChildren().entrySet()) {

            FunctionNode nextNode = entry.getValue();

            this.visitFunctionNode(nextNode, functionNodeVisitor);
        }
        visited = true;

        return visited;

    }

    public boolean addFunctionScript(FunctionNode currentFunctionNode, FunctionScript functionScript) {
        boolean status = true;
        if (currentFunctionNode.isRoot()) {
            FunctionNode childFunctionNode = (FunctionNode) currentFunctionNode.findFunctionNode(currentFunctionNode, functionScript.getFunctionName());
            if (childFunctionNode == null) {
                childFunctionNode = new FunctionNode(functionScript.getFunctionName());
                childFunctionNode.setNodeType(NodeType.FUNCTION);
                currentFunctionNode.addChild(childFunctionNode);
            }

            return addFunctionScript(childFunctionNode, functionScript);
        }

        ParameterScript paramScript = functionScript.getParameterScript();

        FunctionNode tmpNode = currentFunctionNode;

        if (!paramScript.isEmpty()) {
            for (Map.Entry<Variable, IObjectScript> entry : paramScript.getParamScripts().entrySet()) {
                String mangledParamType = entry.getValue().mangle();
                FunctionNode childParamNode = new FunctionNode(mangledParamType);
                childParamNode.setKey(mangledParamType);
                childParamNode.setNodeType(NodeType.PARAMETER);
                tmpNode = tmpNode.addChild(childParamNode);

            }
        }

        tmpNode.setValue(functionScript);

        return status;
    }

    
}
