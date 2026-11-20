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

    public Phrase getOnly() throws IllegalStateException{
        if(phrases.size() != 1)
            throw new IllegalStateException("AmbiguousPhrase has more then one meaning");
        return phrases.getFirst();
    }

    public <T extends Phrase> int count(Class<T> phrase){
        int amount = 0;
        for (Phrase p : phrases) {
            if(phrase.isInstance(p))
                amount++;
        }
        return amount;
    }

    public <T extends Phrase> boolean contains(Class<T> phrase){
        return phrases.stream().anyMatch(p -> phrase.isInstance(p));
    }

    public <T extends Phrase> ArrayList<T> getAll(Class<T> phrase){
        ArrayList<T> out = new ArrayList<>();
        for(Phrase p : phrases){
            if(phrase.isInstance(p)){
                out.add(phrase.cast(p));
            }
        }
        return out;
    }

    public <T extends Phrase> T get(Class<T> phrase) throws IllegalStateException{
        if(count(phrase) != 1)
            throw new IllegalStateException("Cannot call function: getPhrase with more then one meaning");
        
        for(Phrase p : phrases){
            if(phrase.isInstance(p))
                return phrase.cast(p);
        }
        throw new IllegalStateException("No meaning of the requested type");
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