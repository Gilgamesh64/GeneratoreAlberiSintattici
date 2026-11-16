package org.treemaker.tree;

public class Tree {

    private Node root;

    public Tree() {
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
}