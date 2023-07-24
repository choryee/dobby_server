package com.emgram.kr.dobby.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CaldavDocument {
    private NodeList nodeList;
    public CaldavDocument(NodeList nodeList) {this.nodeList = nodeList;}

    public CaldavDocument find(String nodeName){
        for (int k = 0 ; k < this.nodeList.getLength() ; k++){
            Node node = this.nodeList.item(k);
            String _nodeName = this.getNodeName(node);
            if(_nodeName == null)
                continue;
            if(_nodeName.equals(nodeName)){
                CaldavDocument c = new CaldavDocument(node.getChildNodes());
                return c;
            }
        }
        return null;
    }

    public String text(){
        Node node = this.nodeList.item(0);
        if(node == null)
            return "";
        return node.getNodeValue().replaceAll("\"", "");
    }

    public List<CaldavDocument> list(String nodeName){
        List<CaldavDocument> nodes = new ArrayList<>();
        for(int k = 0 ; k < this.nodeList.getLength() ; k++){
            Node node = this.nodeList.item(k);
            String _nodeName = this.getNodeName(node);
            if(_nodeName == null)
                continue;
            if(_nodeName.equals(nodeName))
                nodes.add(new CaldavDocument(node.getChildNodes()));
        }
        return nodes;
    }

    private String getNodeName(Node node){
        String _nodeName = node.getNodeName();
        if(_nodeName.contains("#"))
            return null;
        return _nodeName.split(":")[1];
    }
}
