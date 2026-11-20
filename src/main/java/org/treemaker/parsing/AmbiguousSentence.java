package org.treemaker.parsing;

import java.util.ArrayList;

import org.treemaker.phrases.Article;
import org.treemaker.phrases.Noun;
import org.treemaker.phrases.Phrase;
import org.treemaker.phrases.Verb;

public class AmbiguousSentence{
    private ArrayList<AmbiguousPhrase> phrases;

    public AmbiguousSentence(){
        phrases = new ArrayList<>();
    }

    public void add(Phrase s){
        if(phrases.isEmpty() || !s.getForm().equals(phrases.getLast().getAny().getForm())){
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

            if(ap.countMeanings() == 1){
                sentence.add(ap.getOnly());
            }
            else{
                if(ap.containsMeaning(Article.class) && !sentence.contains(Article.class)){
                    sentence.add(ap.getPhrase(Article.class));
                    continue;
                }
                if(ap.containsMeaning(Noun.class) && !sentence.contains(Noun.class)){
                    sentence.add(ap.getPhrase(Noun.class));
                    continue;
                }
                if(ap.containsMeaning(Verb.class) && !sentence.contains(Verb.class)){
                    for (Verb v : ap.getAllPhrases(Verb.class)) {
                        if(v.getMode().equals("impr") && sentence.contains(Noun.class))
                            continue;
                        if(sentence.contains(Verb.class)){
                            System.out.println("Ambiguous verb cannot be resolved: " + v);
                        }
                        else
                            sentence.add(v);
                    }
                    continue;
                }
                
            }
        }
        
        return sentence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AmbiguousPhrase ap : phrases) {
            sb.append(ap).append("\n\n");
        }
        return sb.toString();
    }
}