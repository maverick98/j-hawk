/*
 * This file is part of j-hawk
 * CopyLeft (C) BigBang<->BigCrunch Manoranjan Sahu, All Rights are left.
 * 

 */
package org.commons.ds.graph;

import org.commons.ds.graph.service.GraphServiceImpl;
import org.commons.ds.graph.service.IGraphService;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.commons.file.FileUtil;
import org.commons.string.StringUtil;

/**
 *
 * @author manosahu
 */
public class GraphFactory {

    private static  Map<String, Node> map = new HashMap<>();

    public static Graph<String> createGrpah(String file) {
        Map<Integer, String> inputMap = FileUtil.dumpFileToMap(file);
        Graph<String> graph = new Graph<>();

        if (inputMap != null && !inputMap.isEmpty()) {
            for (Map.Entry<Integer, String> entry : inputMap.entrySet()) {
                String line = entry.getValue();
                if (!StringUtil.isNullOrEmpty(line)) {
                    Node<String> node = createNode(line);
                    graph.addNode(node);
                }

            }
        }
        return graph;
    }

    private static Node<String> createNode(String line) {
        if (StringUtil.isNullOrEmpty(line)) {
            return null;
        }
        int colon = line.indexOf(":");
        String nodeStr = line.substring(0, colon);
        Node<String> node = map.get(nodeStr);
        if (node == null) {
            node = new Node<>();
            map.put(nodeStr, node);
        }
        node.setPayload(nodeStr);
        String edgeListStr = line.substring(colon + 1);
        StringTokenizer strTok = new StringTokenizer(edgeListStr, ",");
        while (strTok.hasMoreTokens()) {
            String edgeNodeStr = strTok.nextToken();
            Node<String> edgeNode = map.get(edgeNodeStr);
            if (edgeNode == null) {
                edgeNode = new Node<>();
                map.put(edgeNodeStr, edgeNode);
            }
            edgeNode.setPayload(edgeNodeStr);
            node.addUndirectedEdge(edgeNode);
        }
        return node;
    }
    
    public static void reset(){
        map = new HashMap<>();
    }

  
}
