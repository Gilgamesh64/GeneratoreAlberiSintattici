package org.treemaker.parsing;

import java.util.ArrayList;

import org.treemaker.phrases.Phrase;


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

    public Phrase getOnly() throws IllegalStateException{
        if(meanings.size() != 1)
            throw new IllegalStateException("AmbiguousPhrase has more then one meaning");
        return meanings.getFirst();
    }

    public int countMeanings(){
        return meanings.size();
    }

    public <T extends Phrase> int countMeanings(Class<T> meaning){
        int amount = 0;
        for (Phrase p : meanings) {
            if(meaning.isInstance(p))
                amount++;
        }
        return amount;
    }

    public <T extends Phrase> boolean containsMeaning(Class<T> meaning){
        for (Phrase p : meanings) {
            if(meaning.isInstance(p))
                return true;
        }
        return false;
    }

    public <T extends Phrase> ArrayList<T> getAllPhrases(Class<T> meaning){
        ArrayList<T> out = new ArrayList<>();
        for(Phrase p : meanings){
            if(meaning.isInstance(p)){
                out.add(meaning.cast(p));
            }
        }
        return out;
    }

    public <T extends Phrase> T getPhrase(Class<T> meaning) throws IllegalStateException{
        if(countMeanings(meaning) != 1)
            throw new IllegalStateException("Cannot call function: getPhrase with more then one meaning");
        
        for(Phrase p : meanings){
            if(meaning.isInstance(p))
                return meaning.cast(p);
        }
        throw new IllegalStateException("No meaning of the requested type");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Start of the AmbiguousPhrase: \n");
        for(Phrase p : meanings){
            sb.append(p).append("\n");
        }
        sb.append("End of the AmbiguousPhrase");
        return sb.toString();
    }
}