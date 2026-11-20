package org.treemaker.tree;

import org.treemaker.phrases.Phrase;

public class Node{
    private Node left;
    private Node right;
    private final String content;
    private Phrase phrase;

    public Node(String content){
        this.content = content;
        this.phrase = null;
    }
    public Node(String content, Phrase phrase){
        this.content = content;
        this.phrase = phrase;
    }

    public Node getLeft(){ return left; }
    public Node getRight(){ return right; }
    public void setLeft(Node l){ left = l; }
    public void setRight(Node r){ right = r; }

    public String getContent() { return content; }
    public Phrase getPhrase() { return phrase; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(content);
        
        if(phrase != null)
            sb.append("  ").append(phrase.getForm());

        return sb.toString();
    }
}