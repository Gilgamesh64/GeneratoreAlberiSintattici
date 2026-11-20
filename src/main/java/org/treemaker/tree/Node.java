package org.treemaker.tree;

import org.treemaker.phrases.Phrase;

public class Node{
    protected Node left;
    protected Node right;
    protected final String value;
    protected Phrase phrase;

    public Node(String content){
        this.value = content;
        this.phrase = null;
    }
    public Node(String content, Phrase phrase){
        this.value = content;
        this.phrase = phrase;
    }

    public Node getLeft(){ return left; }
    public Node getRight(){ return right; }
    public void setLeft(Node l){ left = l; }
    public void setRight(Node r){ right = r; }

    public String getValue() { return value; }
    public Phrase getPhrase() { return phrase; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(value);
        
        if(phrase != null)
            sb.append("  ").append(phrase.getForm());

        return sb.toString();
    }
}