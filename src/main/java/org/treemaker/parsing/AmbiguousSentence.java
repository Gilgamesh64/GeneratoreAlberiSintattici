package org.treemaker.parsing;

import java.util.ArrayList;

public class AmbiguousSentence{
    private ArrayList<AmbiguousPhrase> phrases;

    public AmbiguousSentence(){
        phrases = new ArrayList<>();
    }

    public void add(Phrase s){
        if(phrases.isEmpty() || !s.forma.equals(phrases.getLast().getAny().forma)){
            AmbiguousPhrase as = new AmbiguousPhrase();
            as.addMeaning(s);
            phrases.add(as);
            return;
        }
        else {
            phrases.getLast().addMeaning(s);
        }
    }

    public Sentence solve(){
        Sentence sentence = new Sentence();
        for (AmbiguousPhrase ap : phrases) {
            Phrase any = ap.getAny();
            sentence.add(
                Phrase.from(any.forma, any.lemma, ap.size() == 1 ? any.caratteristiche : "UNKNOWN"));
            
        }
        return sentence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AmbiguousPhrase ap : phrases) {
            sb.append(ap).append("\n\n\n");
        }
        return sb.toString();
    }
}