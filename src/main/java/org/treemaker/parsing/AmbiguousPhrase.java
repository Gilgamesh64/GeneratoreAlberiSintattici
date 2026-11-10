package org.treemaker.parsing;

import java.util.ArrayList;

public class AmbiguousPhrase {
    private ArrayList<Phrase> meanings;

    public AmbiguousPhrase(){
        meanings = new ArrayList<>();
    }

    public void addMeaning(Phrase meaning){
        meanings.add(meaning);
    }

    public Phrase getAny(){
        return meanings.getFirst();
    }

    public int size(){
        return meanings.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Start of the AmbiguousPhrase: \n");
        for(Phrase p : meanings){
            sb.append(p).append("\n\n");
        }
        sb.append("End of the AmbiguousPhrase");
        return sb.toString();
    }
}