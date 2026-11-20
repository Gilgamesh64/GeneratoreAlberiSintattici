package org.treemaker.tree;

import org.treemaker.parsing.Sentence;
import org.treemaker.phrases.Article;
import org.treemaker.phrases.Noun;
import org.treemaker.phrases.Verb;

public class Tree {

    protected  Node root;
    private Sentence sentence;

    public Tree(Sentence sentence) {
        this.sentence = sentence;

        if(sentence.contains(Verb.class)){
            root = new Node("TP");
            writeTP(root);
        }
        if(!sentence.contains(Verb.class) && !sentence.contains(Article.class) && sentence.contains(Noun.class)){
            root = new Node("NP");
            writeNP(root);
        }
        
    }

    private void writeTP(Node tp){
        tp.setLeft(new Node("DP"));
        writeDP(tp.getLeft());

        Node t = new Node("T'");
        tp.setRight(t);
        t.setLeft(new Node("T", sentence.get(Verb.class)));
        t.setRight(new Node("VP"));
        writeVP(t.getRight());
    }

    private void writeVP(Node vp){
        if(sentence.contains(Noun.class) && !sentence.contains(Article.class)){
            vp.setLeft(new Node("NP"));
            writeNP(vp.getLeft());
        }
        if(sentence.contains(Noun.class) && sentence.contains(Article.class)){
            vp.setLeft(new Node("DP"));
            writeDP(vp.getLeft());
        }
        Node v = new Node("V'");
        vp.setRight(v);
        Node verb = new Node("V", sentence.get(Verb.class));
        v.setLeft(verb);
    }

    private void writeNP(Node np){
        Node n = new Node("N'");
        np.setRight(n);
        Node noun = new Node("N", sentence.get(Noun.class));
        n.setLeft(noun);
    }

    private void writeDP(Node ap){
        Node a = new Node("D'");
        ap.setRight(a);
        Node article = new Node("D", sentence.get(Article.class));
        a.setLeft(article);
        if(sentence.contains(Noun.class)){
            a.setRight(new Node("NP"));
            writeNP(a.getRight());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(root, sb, "", true);
        return sb.toString();
    }

    private void printTree(Node node, StringBuilder sb, String prefix, boolean isTail) {
        if (node == null)
            return;

        if (node.getRight() != null) {
            printTree(node.getRight(), sb,
                    prefix + (isTail ? "|   " : "    "), false);
        }

        sb.append(prefix)
                .append(isTail ? "|--" : "|-- ")
                .append(node)
                .append('\n');

        if (node.getLeft() != null) {
            printTree(node.getLeft(), sb,
                    prefix + (isTail ? "    " : "|   "), true);
        }
    }

    public String toJson() {
        return nodeToJson(root);
    }

    private String nodeToJson(Node node) {
        if (node == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder("{");
        sb.append("\"value\":\"").append(escapeJson(String.valueOf(node.value))).append("\",");
        if(node.phrase != null) sb.append("\"word\":\"").append(escapeJson(node.phrase.getForm())).append("\",");
        sb.append("\"left\":").append(nodeToJson(node.left)).append(",");
        sb.append("\"right\":").append(nodeToJson(node.right)).append("}");

        return sb.toString();
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}