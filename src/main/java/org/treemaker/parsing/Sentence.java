package org.treemaker.parsing;

import java.util.ArrayList;

import org.treemaker.phrases.Phrase;

public class Sentence{
    private ArrayList<Phrase> phrases;

    public Sentence(){
        phrases = new ArrayList<>();
    }

    public void add(Phrase phrase){
        phrases.add(phrase);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Phrase s : phrases) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}