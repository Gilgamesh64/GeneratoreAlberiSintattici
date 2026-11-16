package org.treemaker.tree;

import org.treemaker.parsing.Phrase;

public class Node{
    private Node left;
    private Node right;
    private final String content;
    private Phrase phrase;

    public Node(String content){
        this.content = content;
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
        return content;
    }
}